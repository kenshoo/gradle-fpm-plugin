/*
* Copyright 2012 Kenshoo.com
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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class PackagingTask extends DefaultTask {
    def FPM = "fpm"
    def dependencies
    def prefix
    def type
    def baseDir
    def filesArgs

    PackagingTask(String type){
        this.type = type
    }

    @TaskAction
    debian() {
        initConfiguration()
        def outDir = createOutDir()

        def fpmArgs = getArgs()
        logger.info('running fpm with: ' + fpmArgs)
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
        def debVersion = project.version.replace("-SNAPSHOT", "")
        def fpmArgs = ["-t", type, "-s", "dir", "-n", "${project.name}", "-v", "$debVersion","-C", baseDir]
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
        if (filesArgs instanceof Collection) {
            filesArgs.each {
                arr << it
            }
        } else {
            arr << filesArgs
        }
        arr
    }

    def initConfiguration() {
        dependencies = project.debian.dependencies
        prefix = project.debian.prefix
        filesArgs = project.debian.filesArgs ? project.debian.filesArgs : "."
        baseDir = project.debian.baseDir? project.debian.baseDir : project.buildDir
    }

    def createOutDir() {
        def outDir = new File(project.buildDir, "/deb")
        outDir.mkdir()
        outDir
    }
}