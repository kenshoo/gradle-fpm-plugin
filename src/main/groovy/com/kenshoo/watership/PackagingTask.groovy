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

import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jruby.embed.ScriptingContainer
import org.jruby.embed.LocalContextScope
import org.jruby.embed.LocalVariableBehavior

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
            def paths = new ArrayList();
            paths.add("fpm/gems/arr-pm-0.0.7/lib/");
            paths.add("fpm/gems/backports-2.6.2/lib/");
            paths.add("fpm/gems/cabin-0.4.4/lib/");
            paths.add("fpm/gems/clamp-0.3.1/lib/");
            paths.add("fpm/gems/fpm-0.4.20/lib/");
            paths.add("fpm/gems/json-1.6.6-java/lib/");
            def sc = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
            sc.setLoadPaths(paths);
            sc.runScriptlet("require 'rubygems'")
            sc.runScriptlet("require 'fpm'")
            sc.runScriptlet("pkg = FPM::Package::Gem.new")
            sc.runScriptlet("pkg.input('${stageDir.getAbsoluteFile()}')")
            sc.runScriptlet("p = pkg.convert(FPM::Package::${type})")
            sc.runScriptlet("p.name = ${project.name}")
            sc.runScriptlet("p.version = ${debVersion}")
            sc.runScriptlet("begin; p.output(rpm.to_s(${outDir.getAbsolutePath()}${File.separator}${project.name})); ensure; p.cleanup; end")
            
            /*project.exec {
                commandLine FPM
                args fpmArgs
                workingDir outDir.getAbsolutePath()
            }*/
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

