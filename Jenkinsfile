pipeline {
    agent {node { label "nightly-general-docker"}}
    options { timestamps() }
    stages {

            stage('checkout') {
                steps {

					sh '''
						ls
					   '''
                }
            }
	}
}
