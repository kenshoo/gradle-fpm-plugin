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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class StagePackageFilesTaskTest extends Specification {
    def baseDir

    def setup() {
        baseDir = new File(UUID.randomUUID().toString())
        baseDir.mkdir()
    }

    def cleanup() {
        baseDir.deleteDir()
    }

    def 'copy task copies yaml and jar to stage dir when no patterns provided'(){
        given:
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.version = "0.1.6-SNAPSHOT"
            File buildDir = new File(baseDir, 'build')
            File resourcesDir = new File(buildDir, '/resources/main')
            resourcesDir.mkdirs()
            File libsDir = new File(buildDir, 'libs')
            libsDir.mkdir()
            new File(resourcesDir, 'test.yml').createNewFile()
            new File(libsDir, 'test-0.1.6-SNAPSHOT.jar').createNewFile()
            StagePackageFilesTask task = project.tasks['stagePackageFiles']
        when:
            task.stageFiles()
        then:
            new File(buildDir,'/package-stage/test.yml').exists()
            new File(buildDir,'/package-stage/test-0.1.6-SNAPSHOT.jar').exists()

    }



    def 'supports fileset patterns'() {
        given:
            def dir1 = new File(baseDir, "dir1")
            dir1.mkdir()
            def dir2 = new File(baseDir, "dir2")
            dir2.mkdir()
            new File(dir1, "file1.txt").createNewFile()
            new File(dir1, "file2.txt").createNewFile()
            new File(dir2, "file3.txt").createNewFile()
            new File(dir2, "file.jar").createNewFile()
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.debian.sourceDirs = [dir1, [dir2, "*.jar"]]
            StagePackageFilesTask task = project.tasks['stagePackageFiles']
        when:
            task.execute()
        then:
            new File(baseDir,'/build/package-stage/file1.txt').exists()
            new File(baseDir,'/build/package-stage/file2.txt').exists()
            new File(baseDir,'/build/package-stage/file.jar').exists()
            !new File(baseDir,'/build/package-stage/file3.txt').exists()
    }

    def 'copies with full path'() {
        given:
            def dir1 = new File(baseDir, "dir1")
            dir1.mkdir()
            def dir2 = new File(dir1, "dir2")
            dir2.mkdir()
            new File(dir2, "file.txt").createNewFile()
            new File(dir2, "file.jar").createNewFile()
            Project project = ProjectBuilder.builder().withProjectDir(baseDir).build()
            project.apply plugin:  'java'
            project.apply plugin: 'fpm-packaging'
            project.debian.sourceDirs = [[dir1, "dir2/**"]]
            StagePackageFilesTask task = project.tasks['stagePackageFiles']
        when:
            task.execute()
        then:
            new File(baseDir,'/build/package-stage/dir2/file.jar').exists()
            new File(baseDir,'/build/package-stage/dir2/file.txt').exists()
    }
}
