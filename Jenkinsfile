pipeline {
    agent {node { label "nightly-general-docker"}}
    options { timestamps() }
    stages {

            stage('ls') {
                steps {
			sh label: '', script: 'ls'
                }
            }
	}
}
