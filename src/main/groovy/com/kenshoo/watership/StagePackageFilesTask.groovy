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

class StagePackageFilesTask extends DefaultTask {
    def stageDir

    @TaskAction
    stageFiles() {
        stageDir = createStageDir()
        def ant = new AntBuilder()
        project.debian.sourceDirs.each  { source ->
            if (source instanceof Collection) {
                ant.copy(toDir: stageDir.absolutePath , overwrite: true, failOnError: true) {
                    fileset (dir: source[0]) {
                        include(name: source[1])
                    }
                }
            } else {
                ant.copy(toDir: stageDir.absolutePath , overwrite: true, failOnError: true) {
                    fileset (dir: source) {
                        include(name:  "**")
                    }

                }
            }

        }
    }

    def createStageDir() {
        def stageDir = new File(project.buildDir, PackagingPlugin.STAGE_PATH)
        stageDir.mkdirs()
        stageDir
    }
}
