gradle-fpm-plugin
=================

a gradle plugin to create linux package (deb/rpm) using [fpm](https://github.com/jordansissel/fpm)

you must have fpm installed and available

## Usage
	// Grab the plugin from a Maven Repo automatically
	buildscript {
	    repositories {
		mavenCentral()
	    }
	    dependencies {
		    classpath 'com.kenshoo:gradle-frpm:0.2'
	    }
	}

     apply plugin: 'fpm-packaging'

	//to create deb package
	debian {
	    dependecies = ['openjdk-6-jre', 'tomcat7'] //Optional, an array of package dependencies
	    baseDir = project.buildDir// Optional, base directory to package, default: project.buildDir
	    prefix = // Optional, a path to prefix files when building package
	    filesArgs = //Optional, array of files/dirs to package, relative to baseDir, default: .
	}

	//to create a rpm package
	rpm {
	    //same properties as debian
	}

The package is created under project.buildDir/deb

## License
This code is released under the Apache Public License 2.0.