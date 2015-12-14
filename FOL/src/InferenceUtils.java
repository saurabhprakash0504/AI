import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InferenceUtils {

	public static Predicate getPredicate(String statement) {
		// TODO Auto-generated method stub

		String regex = "([A-Za-z0-9]+)\\(([ ,.a-zA-Z0-9]+)\\)";
		Pattern funcPattern = Pattern.compile(regex);
		Predicate p = new Predicate();
		try {
			if (statement.startsWith("~")) {
				p.ifNegate = true;
			}
			statement = statement.replaceAll("~", "");
			Matcher m = funcPattern.matcher(statement);

			System.out.println("Match found: " + m.matches());
			System.out.println("Total matches are: " + m.groupCount());
			if (m.matches()) {
				for (int index = 0; index <= m.groupCount(); index++) {
					System.out.println("Group number " + index + "is: "
							+ m.group(index));
				}
			}

			p.predicateName = m.group(1);

			String[] arguments = m.group(2).split(",");
			List<Term> terms = createTerms(Arrays.asList(arguments));
			p.termList = terms;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	private static List<Term> createTerms(List<String> asList) {
		List<Term> terms = new ArrayList<Term>();
		// TODO Auto-generated method stub
		for (String s : asList) {
			Term t = new Term();

			if (java.lang.Character.isUpperCase(s.charAt(0))) {
				t.isConstant = true;
				t.value = s;
				//System.out.println("Constant");
			}
			if (java.lang.Character.isLowerCase(s.charAt(0))) {
				t.isVariable = true;
				t.value = "";
				t.variableName = s;
				//System.out.println("Variable");
			}
			terms.add(t);
		}
		return terms;
	}

	public static Sentence createSentence(String statement) {
		Sentence s = new Sentence();
		if (statement.contains("=>")) {
			s.isLiteral = false;
			String[] premConc = statement.split("=>");
			String premise = premConc[0];
			if (premise.contains("^")) {

				String[] premises = premise.split(Pattern.quote("^"));
				for (int i = 0; i < premises.length; i++) {
					Predicate p = getPredicate(premises[i].trim());

					s.pList.add(p);
				}
			} else {
				Predicate p = getPredicate(premise.trim());
				s.pList.add(p);
			}

			String conclusion = premConc[1];
			Predicate p = getPredicate(conclusion.trim());
			s.conclusion = p;

		} else {
			s.isLiteral = true;
			Predicate p = getPredicate(statement);
			s.conclusion = p;
		}
		return s;
	}

	public static boolean processQuery(Predicate qPredicate, KnowledgeBase kb) {
		boolean result = false;
		result = kb.ask(qPredicate);

		// TODO Auto-generated method stub
		return result;
	}

}
