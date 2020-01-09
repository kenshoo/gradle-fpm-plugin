gradle-fpm-plugin
=================

a gradle plugin to create linux package (deb/rpm) using [fpm](https://github.com/jordansissel/fpm)

you must have fpm installed and available

## Usage
```groovy
// Grab the plugin from a Maven Repo automatically
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.kenshoo:gradle-fpm:0.5'
    }
}

apply plugin: 'fpm-packaging'

//to create deb package
packaging {
    packageName = 'my-package' // Optional, default is project.name
    dependencies = ['openjdk-6-jre', 'tomcat7'] //Optional, an array of package dependencies
    baseDir = project.buildDir// Optional, base directory to package, default: project.buildDir
    prefix = /opt/my-process // Optional, a path to prefix files when building package, default: root (/)
    extraOptions = ['--vendor': 'MyCompany', '--maintainer': 'me'] // Optional, a map containing extra options
    extraFlags   = ['--rpm-auto-add-directories'] // Optional, a list of additional flags to fpm
    filesArgs = ['dir1', 'file1']//Optional, array of files/dirs to package, relative to baseDir, default: .
    force     = [true / false ] //optional, force output even if overwrite, default: false
}

//to create a rpm package
rpm {
    //same properties as debian
}
```

The package is created under project.packaging.packageDir (default: buildDir/linux-package)

## License
This code is released under the Apache Public License 2.0.
