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
    def extraOptions
    def filesArgs
    def force

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
        def version = project.version
        def fpmArgs = ["-t", type, "-s", "dir", "-n", "${project.name}", "-v", "$version","-C", baseDir]
        if (prefix)
            fpmArgs.addAll(["--prefix", prefix])
        dependencies.each() {
            fpmArgs << "-d"
            fpmArgs << it
        }
        if (force)
            fpmArgs << '-f'
        def packageFiles = getStageFiles()
        if (extraOptions instanceof Map) {
            extraOptions.each {
                fpmArgs.addAll([it.key, it.value])
            }
        }
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
        dependencies = project.packaging.dependencies
        prefix = project.packaging.prefix
        filesArgs = project.packaging.filesArgs ? project.packaging.filesArgs : "."
        extraOptions = project.packaging.extraOptions
        baseDir = project.packaging.baseDir? project.packaging.baseDir : project.buildDir
        force = project.packaging.force ?: false
    }

    def createOutDir() {
        def outDir = project.packaging.packageDir
        if (!outDir.exists())
            outDir.mkdirs()
        outDir
    }
}
