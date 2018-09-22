name := "linearCalendar"

version := "0.1"

scalaVersion := "2.12.6"

// https://mvnrepository.com/artifact/com.itextpdf/root
libraryDependencies += "com.itextpdf" % "root" % "7.1.3" pomOnly()

// https://mvnrepository.com/artifact/com.itextpdf/io
libraryDependencies += "com.itextpdf" % "io" % "7.1.3"

// https://mvnrepository.com/artifact/com.itextpdf/layout
libraryDependencies += "com.itextpdf" % "layout" % "7.1.3"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
