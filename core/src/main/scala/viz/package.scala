/*
 * Copyright 2013 Nelson Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//: ----------------------------------------------------------------------------
//: Copyright (C) 2015 Verizon.  All Rights Reserved.
//:
//:   Licensed under the Apache License, Version 2.0 (the "License");
//:   you may not use this file except in compliance with the License.
//:   You may obtain a copy of the License at
//:
//:       http://www.apache.org/licenses/LICENSE-2.0
//:
//:   Unless required by applicable law or agreed to in writing, software
//:   distributed under the License is distributed on an "AS IS" BASIS,
//:   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//:   See the License for the specific language governing permissions and
//:   limitations under the License.
//:
//: ----------------------------------------------------------------------------
package quiver

package object viz {

  /** Formats a graph for use with graphviz */
  def graphviz[N, A, B](
      g: Graph[N, A, B],
      title: String = "fgl",
      pageSize: (Double, Double) = (8.5, 11.0),
      gridSize: (Int, Int) = (1, 1),
      orient: Orient = Landscape
  ): String = {
    def sn(node: LNode[N, A]) =
      node match {
        case LNode(vertex, a) =>
          val sa = sl(a)
          if (sa == "") "" else s"\t$vertex$sa\n"
      }
    def se(edge: LEdge[N, B]) =
      edge match {
        case LEdge(n1, n2, b) => s"""\t"$n1" -> "$n2"${sl(b)}\n"""
      }
    val n  = g.labNodes
    val e  = g.labEdges
    val ns = (n flatMap sn).mkString
    val es = (e flatMap se).mkString
    def sz(w: Double, h: Double) =
      if (orient == Portrait) s"$w,$h" else s"$h,$w"
    val ps = s"${pageSize._1},${pageSize._2}"
    val (pw, ph) =
      if (orient == Portrait) gridSize else (gridSize._2, gridSize._1)
    val gs = sz(pageSize._1 * pw, pageSize._2 * ph)

    s"digraph $title {\n" +
      "\tmargin = \"0\"\n" +
      s"""\tpage = "${ps}"\n""" +
      s"""\tsize = "${gs}"\n""" +
      orient +
      "\tratio = \"fill\"\n" +
      ns +
      es +
      "}"
  }

  private def sq(s: String) =
    if (s.size == 1) s
    else if (s.startsWith("\""))
      s.substring(1, s.size - (if (s.endsWith("\"")) 1 else 0))
    else if (s.startsWith("'"))
      s.substring(1, s.size - (if (s.endsWith("'")) 1 else 0))
    else s

  private def sl[A](a: A) = {
    val l = sq(a.toString)
    if (l != "()") s""" [label = "${l}"]""" else ""
  }
}
