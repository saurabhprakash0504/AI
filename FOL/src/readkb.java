import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class readkb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		PrintWriter writer = null;
		try {

			br = new BufferedReader(new FileReader(
					"C:\\ALL_WORK_SPACE\\AI\\FOL\\sunMorn\\src\\input_1.txt"));
			writer = new PrintWriter("output.txt", "UTF-8");
			InferenceUtils infer = new InferenceUtils();

			String line = br.readLine();
			List<Predicate> queries = new ArrayList<Predicate>();
			int noQueries = Integer.parseInt(line.trim());
			for (int i = 0; i < noQueries; i++) {
				String line2 = br.readLine();
				System.out.println("line" + line2);
				Predicate query = infer.getPredicate(line2);
				System.out.println(query);
				queries.add(query);
			}

			line = br.readLine();
			List<Sentence> sentences = new ArrayList<Sentence>();
			int noSent = Integer.parseInt(line.trim());
			for (int i = 0; i < noSent; i++) {
				Sentence s = infer.createSentence(br.readLine());
				sentences.add(s);
				// System.out.println(s);
			}
			KnowledgeBase kb = new KnowledgeBase(sentences);

			for (Predicate qPredicate : queries) {
				try {
					boolean result = infer.processQuery(qPredicate, kb);
					String res = Boolean.toString(result).toUpperCase();
					System.out.println(res);
					// System.out.println(qPredicate);
				} catch (Error r) {
					r.printStackTrace();
				}// System.gc();
					// kb = null;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);

		} finally {
			try {
				br.close();
				writer.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
		}
	}
}
