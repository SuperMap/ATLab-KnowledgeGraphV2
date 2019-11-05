package www.sunyasong.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.jena.datatypes.xsd.XSDDateTime;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
/**
 * jena中给rdf生成时间标签
 * @author SunYasong
 *
 */
public class TimeLabel {

	public TimeLabel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		  String SOURCE = "http://www.w3.org/2002/07/owl#";
	        Model model = ModelFactory.createDefaultModel();
	        DateFormat df = new SimpleDateFormat("dd-MM-yy");
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(df.parse("18-7-1495"));
	        Resource testResource = model.createResource(SOURCE + "test");
	        testResource.addProperty(DCTerms.date, model.createTypedLiteral(new XSDDateTime(cal)));
	        model.write(System.out);
	}

}
