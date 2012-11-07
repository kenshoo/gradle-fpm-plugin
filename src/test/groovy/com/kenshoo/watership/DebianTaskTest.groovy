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

import org.gradle.api.Project
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class DebianTaskTest extends Specification{
    def baseDir
    def stageDir

    def setup() {
        baseDir = new File(UUID.randomUUID().toString())
        stageDir  = new File(baseDir, "/build/package-stage")
        stageDir.mkdirs()

    }

    def cleanup() {
        baseDir.deleteDir()
    }

    def 'creates deb packages on execute'() {
        given:
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.version = '0.1.6-SNAPSHOT'
            File yamlFile = new File(stageDir, 'test.yml')
            yamlFile.createNewFile()
            File jarFile = new File(stageDir, 'test-0.1.6-SNAPSHOT.jar')
            jarFile.createNewFile()
            def task =project.tasks["debian"]
            task.stageDir = stageDir
        when:
            task.execute()
        then:
            new File(baseDir,'/build/deb/test_0.1.6_amd64.deb').exists()
    }

    def 'gets fpm not installed message'() {
        given:
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.version = '0.1.6-SNAPSHOT'
            File yamlFile = new File(stageDir, 'test.yml')
            yamlFile.createNewFile()
            File jarFile = new File(stageDir, 'test-0.1.6-SNAPSHOT.jar')
            jarFile.createNewFile()
            def task =project.tasks["debian"]
            task.stageDir = stageDir
            task.FPM = "not"
            when:
            task.execute()
        then:
            thrown(TaskExecutionException)
            !(new File(baseDir,'/build/deb/test_0.1.6_amd64.deb').exists())
    }

    def 'file args contain relative path of files'() {
        given:
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.version = '0.1.6-SNAPSHOT'
            File file1 = new File(stageDir, 'file1')
            file1.createNewFile()
            File dir = new File(stageDir, "dir")
            dir.mkdir()
            File file2 = new File(dir, "file2")
            file2.createNewFile()
            def task =project.tasks["debian"]
            task.stageDir = stageDir
        when:
            task.execute()
            def args = task.getArgs()
        then:
            args.contains('file1')
            args.contains('dir/file2')
            !args.contains('--prefix')
    }

    def 'args contain prefix and deps when given'() {
        given:
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.debian.prefix = "/opt/xxx/yyy"
            project.debian.dependencies = ['cassandra', 'java7']
            project.version = '0.1.6-SNAPSHOT'
            new File(stageDir, 'file1').createNewFile()
            def task =project.tasks["debian"]
            task.stageDir = stageDir
        when:
            task.execute()
            def args = task.getArgs()
        then:
            args.contains('--prefix')
            args.contains('/opt/xxx/yyy')
            args.count('-d') == 2
            args.contains('cassandra')
            args.contains('java7')

    }
}
