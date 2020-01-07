pipeline {
agent {label 'general-purpose-new'}
     stages {

            stage('ls') {
                steps {
                    checkout([$class: 'GitSCM', branches: [[name: 'master']],
                    extensions: [[$class: 'CleanCheckout'], [$class: 'RelativeTargetDirectory', relativeTargetDir: 'puppet-module-graylog2']],
                    userRemoteConfigs: [[credentialsId: 'kenshoo-build-for-github', url: 'git@github.com:kenshoo/puppet-module-graylog2.git']]]
                    )
			echo 'Hello world!'
                }
            }
	}
}

