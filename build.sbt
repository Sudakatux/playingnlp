name := "OpenNlp"

version := "0.1"

scalaVersion := "2.12.0"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.apache.opennlp" % "opennlp-maxent" % "3.0.3",
  "org.apache.opennlp" % "opennlp-tools" % "1.8.4",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.5.1",
  "org.scalactic" %% "scalactic" % "3.0.5",
  "joda-time" % "joda-time" % "2.9.3",
  "org.joda" % "joda-convert" % "1.8",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test")