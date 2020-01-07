pipeline {
    agent {node { label "nightly-general-docker"}}
    options { timestamps() }
    stages {

            stage('checkout') {
                steps {

checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: 'master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'kenshoo-build-key', url: 'git@github.com:kenshoo/puppet-module-graylog2.git']]]

					sh '''
						ls
					   '''
                }
            }
	}
}
