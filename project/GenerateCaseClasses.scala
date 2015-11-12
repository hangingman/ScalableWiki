/*
 * This program is written for converting Perl HTML::Template format text
 * to Scala template's one.
 * 
 * Copyright (C) 2015 Hiroyuki Nagata
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributor:
 *	Hiroyuki Nagata <idiotpanzer@gmail.com>
 */

import java.io.File
import java.io.FileOutputStream
import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set
import com.google.common.base.CaseFormat._

class GenerateCaseClasses(initFile: Boolean = true) {

  // init
  new File("./app/model/AutoGeneratedCases.scala") { f: File =>
    f.getParentFile.mkdirs

    if (initFile) {
      f.delete
      f.createNewFile
      printToFile(f) { p =>
        p.println("package models")
        p.println("")
      }
    }
  }

  type WikiMultiMap = HashMap[String, Set[String]] with MultiMap[String, String]

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(new FileOutputStream(f, true))
    try { op(p) } finally { p.close() }
  }

  def generateCaseClasses(mm: WikiMultiMap): HashMap[String, String] = {

    println("Generating case classes for template files...")

    val output = new File("./app/model/AutoGeneratedCases.scala")
    val result = new HashMap[String, String]

    printToFile(output) { p =>
        mm.foreach {
          case(str, set) => 
            println(s"Object ${str} contains ${set}")
            p.println(s"// Object ${str} contains ${set}")

            val objectName = LOWER_UNDERSCORE.to(UPPER_CAMEL, str);
            val companion = set.mkString("(", ": String, ", ": String)")
            result += ((str, "List[" + objectName + "]"))
            p.println(s"case class ${objectName}${companion}")
        }
    }

    result
  }
}
