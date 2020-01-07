pipeline {
    agent { node { label 'general-purpose-new' }}
    options { timestamps() }
    stages {

            stage('checkout') {
                steps {
                    checkout([$class: 'GitSCM', branches: [[name: 'master']], changelog: false, poll: false,
                    extensions: [[$class: 'CleanCheckout'], [$class: 'RelativeTargetDirectory', relativeTargetDir: 'puppet-module-graylog2']],
                    userRemoteConfigs: [[credentialsId: 'kenshoo-build-for-github', url: 'git@github.com:kenshoo/puppet-module-graylog2.git']]]
                    )
					sh '''
						ls
					   '''
                }
            }
	}
}
