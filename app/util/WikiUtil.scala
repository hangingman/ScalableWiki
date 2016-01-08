package jp.gr.java_conf.hangedman.util

import java.io.File
import java.net.URLDecoder
import java.net.URLEncoder
import javax.mail.internet.MimeUtility
import jp.gr.java_conf.hangedman.util.wiki.AbstractWiki
import org.joda.time.DateTime
import play.Logger
import play.api.mvc.AnyContent
import play.api.mvc.Request
import scala.collection.immutable.HashMap
import scala.util.Failure
import scala.util.Success
import scala.util.Try

object WikiUtil {

  def overrideDie() = {}

  /**
   * 引数で渡された文字列をURLエンコードして返します。
   * {{{
   * val str = WikiUtil.urlEncode(str)
   * }}}
   */
  def urlEncode(rawString: String, enc: String = "utf-8"): String = {
    Try {
      URLEncoder.encode(rawString, enc)
    } match {
      case Success(encoded) =>
        encoded
      case Failure(e) =>
        Logger.error(s"Fail to URL encode ${rawString} =>", e)
        ""
    }
  }
  /**
   * 引数で渡された文字列をURLデコードして返します。
   * {{{
   * val str = WikiUtil.urlDecode(str)
   * }}}
   */
  def urlDecode(encString: String, enc: String = "utf-8"): String = {
    Try {
      URLDecoder.decode(encString, enc)
    } match {
      case Success(decoded) =>
        decoded
      case Failure(e) =>
        Logger.error(s"Fail to URL decode ${encString} =>", e)
        ""
    }
  }
  /**
   * Cookieのpathに指定する文字列を取得します。
   * {{{
   * val path = WikiUtil.cookiePath(wiki)
   * }}}
   */
  def cookiePath(wiki: AbstractWiki): String = {
    ""
  }
  /**
   * ディレクトリ、ファイル名、拡張子を結合してファイル名を生成します。
   * {{{
   * val filename = WikiUtil.makeFilename(ディレクトリ名,ファイル名,拡張子)
   * }}}
   */
  def makeFilename(dir: String, file: String, ext: String): String = {
    s"${dir}/${file}/.${ext}"
  }
  /**
   * 引数で渡された文字列のHTMLタグをエスケープして返します。
   * {{{
   * str = WikiUtil.escapeHTML(str)
   * }}}
   */
  def escapeHTML(html: String): String = scala.xml.Utility.escape(html)
  /**
   * 日付を&quot;yyyy年mm月dd日 hh時mi分ss秒&quot;形式にフォーマットします。
   * {{{
   * val date = WikiUtil.formatDate(time())
   * }}}
   */
  def formatDate(dt: DateTime): String = {
    dt.toString("yyyy年MM月dd日 HH時mm分ss秒")
  }
  /**
   * 文字列の両端の空白を切り落とします。
   * {{{
   * val text = WikiUtil.trim(text)
   * }}}
   * @deprecated
   */
  def trim(text: String): String = text.trim
  /**
   * タグを削除して文字列のみを取得します。
   * {{{
   * val html: String = "<B>文字列</B>"
   * // &lt;B&gt;と&lt;/B&gt;を削除し、&quot;文字列&quot;のみ取得
   * val text = WikiUtil.deleteTag(html)
   * }}}
   */
  def deleteTag(text: String): String = {
    """<(.|\s)+?>""".r
      .replaceAllIn(text, m => m.group(1))
  }
  /**
   * 数値かどうかチェックします。数値の場合は真、そうでない場合は偽を返します。
   * {{{
   * if (WikiUtil.checkNumeric(param)) {
   *   // 整数の場合の処理
   * } else {
   *   // 整数でない場合の処理
   * }
   * }}}
   */
  def checkNumeric(text: String) = {
    text.matches("""^[0-9]+$""")
  }
  /**
   * 管理者にメールを送信します。
   * setup.datの設定内容に応じてsendmailコマンドもしくはSMTP通信によってメールが送信されます。
   * どちらも設定されていない場合は送信を行わず、エラーにもなりません。
   * SMTPで送信する場合、このメソッドを呼び出した時点でNet::SMTPがuseされます。
   *
   * {{{
   * WikiUtil.sendMail(wiki,件名,本文)
   * }}}
   */
  def sendMail(wiki: AbstractWiki, rawSubject: String, rawContent: String) = {
    val subject = Try {
      MimeUtility.encodeText(rawSubject)
    } match {
      case Success(mime) =>
        mime
      case Failure(e) =>
        ""
    }
  }
  /**
   * クライアントが携帯電話かどうかチェックします。
   * 携帯電話の場合は真、そうでない場合は偽を返します。
   * {{{
   * if (WikiUtil.handyphone) {
   *   // 携帯電話の場合の処理
   * } else {
   *   // 携帯電話でない場合の処理
   * }
   * }}}
   */
  def handyphone()(implicit request: Request[AnyContent]): Boolean = {
    val userAgent: String = Try {
      request.headers("User-Agent")
    } match {
      case Success(ua) => ua
      case Failure(e) => ""
    }
    if (userAgent.matches("""^DoCoMo\/.*$|^J-PHONE\/.*$|UP\.Browser.*$|\(DDIPOCKET\;.*$|\(WILLCOM\;.*$|^Vodafone\/.*$|^SoftBank\/.*$""")) {
      true
    } else {
      false
    }
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def smartphone(): Boolean = { false }
  /**
   *
   * {{{
   *
   * }}}
   */
  private def unescape() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def loadConfigHash(filename: String): HashMap[String, String] = {
    new HashMap().empty
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def loadConfigText() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def saveConfigHash(filename: String, hash: HashMap[String, String]) = {
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def saveConfigText() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def syncUpdateConfig() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def privatemakeQuotedText() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def fileLock() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def fileUnlock() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def inlineError(message: String, format: String = "WIKI"): String = {
    "Error"
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def paragraphError(message: String, format: String = "WIKI"): String = {
    "Error"
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def getResponse(wiki: AbstractWiki, image: String) = {
    ""
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def getModuleFile(module: String): Option[File] = {
    Try {
      new File(module)
    } match {
      case Success(file: File) =>
        Some(file)
      case Failure(e) =>
        Logger.error(e.getMessage)
        None
    }
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def debug() = {}
  /**
   * java.security.MessageDigestを用いたパスワードの暗号化を行います。
   * 第一引数にパスワード、第二引数にアカウントを渡します。
   *
   * {{{
   * val md5pass = WikiUtil.md5(pass, account)
   * }}}
   */
  def md5(pass: String, salt: String) = {
    val md5 = java.security.MessageDigest.getInstance("MD5")
    md5.update(salt.getBytes)
    md5.digest(pass.getBytes)
      .map("%02x".format(_))
      .mkString
  }
  /**
   *
   * {{{
   *
   * }}}
   */
  def makeContentDisposition() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def die() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def exit() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def restoreDie() = {}
  /**
   *
   * {{{
   *
   * }}}
   */
  def rmtree(file: File): Boolean = {
    org.apache.commons.io.FileUtils.deleteQuietly(file)
  }
}
