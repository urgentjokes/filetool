import java.io.File

fun main(args: Array<String>) {
//  val workingDirectory= File(".").canonicalPath
  val workingDirectory=File("/Users/davefriedman2023/developer-stuff/genkit/docs")
  val outputFile=File("/Users/davefriedman2023/Documents/Genkit_inventory.txt")
  val rawFileList=workingDirectory.listFiles()
  val directoryList=getDirList(rawFileList)
  val listOfMDFiles=getMDFiles(directoryList)
  outputFileAttributes(listOfMDFiles,outputFile)
}

fun getDirList(rawFileList:Array<File>):List<File> {
  val usableList=mutableListOf<File>()
  for (file in rawFileList)
    if (file.isDirectory && !file.name.startsWith(".")) {
      usableList.add(file)
    }
  return usableList
}
fun getMDFiles(directoryList:List<File>):MutableList<MutableList<File>> {
  val fileMap=mutableListOf<MutableList<File>>()
  for (directory in directoryList) {
    val currentDirectory = directory.walkTopDown()
    val individualDirectory=mutableListOf<File>()

    for (file in currentDirectory) {
        if (file.name.endsWith(".md"))
          individualDirectory.add(file)
    }
    fileMap.add(individualDirectory)
  }
  return fileMap
}

fun outputFileAttributes(fileMap:MutableList<MutableList<File>>,
                        outputFile:File) {
  var jumboCounter=0
for (directory in fileMap) {
  var totalSize=0L
  for (file in directory) {
    println (file.canonicalPath.substringAfter("docs")+" "+file.length())
    totalSize+=file.length()
  }
  println("\nTotal number of bytes in directory: ${totalSize}\n")
  if (totalSize > 500000)
    jumboCounter++
}
println ("a total of ${jumboCounter} jumbos.")
}