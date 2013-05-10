/*
 * Copyright 2001-2008 Artima, Inc.
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
package org.scalatest.words

import org.scalatest.matchers._
import org.scalautils._
import scala.util.matching.Regex
import org.scalatest.FailureMessages
import org.scalatest.MatchersHelper.includeRegexWithGroups

/**
 * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="../Matchers.html"><code>Matchers</code></a> for an overview of
 * the matchers DSL.
 *
 * @author Bill Venners
 */
final class IncludeWord {

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlight">
   * "1.7" should (include ("1.7") and include ("1.8"))
   *                       ^
   * </pre>
   */
  def apply(expectedSubstring: String): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          left.indexOf(expectedSubstring) >= 0, 
          FailureMessages("didNotIncludeSubstring", left, expectedSubstring),
          FailureMessages("includedSubstring", left, expectedSubstring)
        )
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlight">
   * val decimal = """(-)?(\d+)(\.\d*)?"""
   * "a1.7b" should (include regex (decimal) and include regex (decimal))
   *                         ^
   * </pre>
   */
  def regex[T <: String](right: T): Matcher[T] = regex(right.r)
  
  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlight">
   * string should not { include regex ("a(b*)c" withGroup "bb") } 
   *                             ^
   * </pre>
   */	
  def regex(regexWithGroups: RegexWithGroups) = 
    new Matcher[String] {
      def apply(left: String): MatchResult = 
        includeRegexWithGroups(left, regexWithGroups.regex, regexWithGroups.groups)
    }

  /**
   * This method enables the following syntax:
   *
   * <pre class="stHighlight">
   * val decimalRegex = """(-)?(\d+)(\.\d*)?""".r
   * "a1.7" should (include regex (decimalRegex) and include regex (decimalRegex))
   *                        ^
   * </pre>
   */
  def regex(expectedRegex: Regex): Matcher[String] =
    new Matcher[String] {
      def apply(left: String): MatchResult =
        MatchResult(
          expectedRegex.findFirstIn(left).isDefined,
          FailureMessages("didNotIncludeRegex", left, expectedRegex),
          FailureMessages("includedRegex", left, expectedRegex)
        )
    }
}
