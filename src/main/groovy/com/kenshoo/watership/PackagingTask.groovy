/*
* Copyright 2011 Kenshoo.com
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.kenshoo.watership

import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * User: liorh
 * Date: 11/7/12
 * Time: 5:38 PM
 */
abstract class PackagingTask extends DefaultTask {
    def FPM = "fpm"
    def stageDir
    def dependencies = []
    def prefix
    def type

    PackagingTask(String type){
        this.type = type
    }

    @TaskAction
    debian() {
        initConfiguration()
        def outDir = createOutDir()

        Object fpmArgs = getArgs()
        try{
            project.exec {
                commandLine FPM
                args fpmArgs
                workingDir outDir.getAbsolutePath()
            }
        } catch (e) {
            if (e.cause instanceof IOException) throw new RuntimeException("make sure fpm is installed and available", e) else throw e
        }
    }

    def getArgs() {
        def stageDir = getStageDir()
        def debVersion = project.version.replace("-SNAPSHOT", "")
        def fpmArgs = ["-t", type, "-s", "dir", "-n", "${project.name}", "-v", "$debVersion", "-C", stageDir.getAbsoluteFile()]
        if (prefix)
            fpmArgs.addAll(["--prefix", prefix])
        dependencies.each() {
            fpmArgs << "-d"
            fpmArgs << it
        }
        def packageFiles = getStageFiles()
        fpmArgs.addAll(packageFiles)
        fpmArgs
    }


    def getStageFiles(){
        def arr=[]
        stageDir.eachFileRecurse(FileType.FILES) { file->
            arr << file.path[stageDir.path.size() + 1..-1]
        }
        arr
    }

    def initConfiguration() {
        dependencies = project.debian.dependencies
        prefix = project.debian.prefix
    }

    def createOutDir() {
        def outDir = new File(project.buildDir, "/deb")
        outDir.mkdir()
        outDir
    }
}

