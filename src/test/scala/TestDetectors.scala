import Analizer.{ConcludedMessage, MDuration}
import org.joda.time.DateTime
import org.scalatest.FunSuite

class TestDetectors extends FunSuite{

  test("Should detect sentences"){
    val justSomeSentences =" This is a Sentence.\n" +
      "This is another"
    assert(Analizer.detectSentences(justSomeSentences).length == 2)
  }

  test("Should tokenize sentences"){
    val justASimpleSentence="This is a simple sentence"
    assert(Analizer.tokenizeSentence(justASimpleSentence).length ==5)
  }

  test("Should parse date according to formatters"){
    val justASimpleSentence="November 22 2015 at 01:30pm"
    assert(Analizer.dateMultiParser(justASimpleSentence).isInstanceOf[DateTime])
  }

  test("Should parse frase"){
    val justASentence = "Please schedule a meeting with Rody Stuart, on November 22 2015 at 01:30pm, for 1 hour, to discuss crap.";
    assert(ConcludedMessage(new DateTime("2015-11-22T13:30:00.000-03:00"),MDuration("1","hour")).equals(Analizer.parseMessage(justASentence)))
  }

}
