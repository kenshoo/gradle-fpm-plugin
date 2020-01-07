pipeline {
    agent {node { label "nightly-general-docker"}}
    options { timestamps() }
    stages {

            stage('checkout') {
                steps {
git changelog: false, credentialsId: 'kenshoo-build-key', poll: false, url: 'git@github.com:kenshoo/puppet-module-graylog2.git'

					sh '''
						ls
					   '''
                }
            }
	}
}
