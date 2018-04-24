import java.io.FileInputStream

import opennlp.tools.namefind.{NameFinderME, NameSampleDataStream, TokenNameFinderFactory, TokenNameFinderModel}
import opennlp.tools.sentdetect.{SentenceDetector, SentenceDetectorME, SentenceModel}
import opennlp.tools.tokenize.{TokenizerME, TokenizerModel}
import opennlp.tools.util.Span
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter, DateTimeFormatterBuilder, DateTimeParser}



object Analizer {
  val sentenceDetector = new SentenceDetectorME(new SentenceModel(new FileInputStream("models/en-sent.bin")))
  val tokenizer = new  TokenizerME(new TokenizerModel(new FileInputStream("models/en-token.bin")))
  val dateFind = new NameFinderME(new TokenNameFinderModel(new FileInputStream("models/namefind/en-ner-date.bin")))

  val dateFormatters:Array[DateTimeParser] = Array("yyyy-MM-dd @ hh:mma","YYYY/MM/dd @ hh:mma", "MMM d yyyy @ hh:mma", "YYYY/MM/dd 'at' hh:mma","MMM d yyyy 'at' hh:mma","YYYY/MM/dd HH:mm")
    .map(DateTimeFormat.forPattern(_).getParser())

  def dateMultiParser(dateString:String)= {
    val dateFormatter:DateTimeFormatter = new DateTimeFormatterBuilder().append(null,dateFormatters).toFormatter()
    DateTime.parse(dateString,dateFormatter)
  }

  def detectSentences(chainOfCharecters:String):Array[String] ={
    sentenceDetector.sentDetect(chainOfCharecters)
  }

  def tokenizeSentence(sentence:String):Array[String] = tokenizer.tokenize(sentence)

  def unifySpans(tokens:Array[String]):String = tokens.mkString(" ")

  def findDateTimeTokens(tokens:Array[String]) = Span.spansToStrings(dateFind.find(tokens),tokens)

  case class MDuration(duration:String,time:String)
  case class ConcludedMessage(startsAt:DateTime, duration:MDuration)


  def parseDuration(tokens:Array[String])={
    val durationIdx = tokens.indexOf("for") // Will work only for this word. TODO should find sinonims
    val duration = tokens(durationIdx+1)
    val dimension = tokens(durationIdx+2)

    MDuration(duration,dimension)

  }


  def parseMessage(message:String):ConcludedMessage={
    val tokens = tokenizeSentence(message)
    val calculateDateStart = dateMultiParser(unifySpans(findDateTimeTokens(tokens)))
    val duration = parseDuration(tokens);
    ConcludedMessage(calculateDateStart,duration)
  }


}
