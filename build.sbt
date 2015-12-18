name := "ansj4s"

version := "1.0"

scalaVersion := "2.11.7"

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

resolvers += "Bigbata" at "http://nexus.bigbata.com/nexus/content/groups/public/"

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "1.5.1"

libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "1.5.1"

libraryDependencies += "org.apache.spark" % "spark-mllib_2.11" % "1.5.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.1"

libraryDependencies += "org.ansj" % "ansj_seg" % "3.0"

libraryDependencies += "org.nlpcn" % "nlp-lang" % "1.0.2"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
