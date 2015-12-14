import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class KnowledgeBase implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7264557235404205594L;
	List<Sentence> senList;
	Map<String, Rule> kbMap = new HashMap<String, Rule>();
	Map<String, String> charArray;

	public KnowledgeBase(List<Sentence> senList) {
		this.senList = senList;
		this.charArray = new HashMap<String, String>();

		construct();

		// TODO Auto-generated constructor stub
	}

	private void construct() {
		Character c = 'a';
		for (int i = 0; i < 26; i++) {
			charArray.put(c.toString(), "");
			c++;
		}
		// TODO Auto-generated method stub
		for (Sentence s : this.senList) {
			if (s.isLiteral) {

				if (kbMap.containsKey(s.conclusion.predicateName)) {

					Rule rule = kbMap.get(s.conclusion.predicateName);
					if (s.conclusion.ifNegate) {
						rule.negLiterals.add(s.conclusion);

					} else {
						rule.posLiterals.add(s.conclusion);

					}

				} else {

					Rule rule = new Rule();

					if (s.conclusion.ifNegate) {
						rule.negLiterals.add(s.conclusion);

					} else {
						rule.posLiterals.add(s.conclusion);

					}

					kbMap.put(s.conclusion.predicateName, rule);
				}
			} else {
				List<String> varList = getVariables(s.conclusion);

				if (kbMap.containsKey(s.conclusion.predicateName)) {
					Rule rule = kbMap.get(s.conclusion.predicateName);
					VarMap varMap = new VarMap();
					varMap.ifNegate = s.conclusion.ifNegate;
					varMap.varList.addAll(varList);
					varMap.prList.addAll(s.pList);
					rule.premises.add(varMap);

				} else {
					Rule rule = new Rule();
					VarMap varMap = new VarMap();
					varMap.ifNegate = s.conclusion.ifNegate;

					varMap.varList.addAll(varList);
					varMap.prList.addAll(s.pList);
					rule.premises.add(varMap);

					kbMap.put(s.conclusion.predicateName, rule);
				}
			}

		}
	}

	private List<String> getVariables(Predicate conclusion) {
		List<String> varList = new ArrayList<String>();
		for (Term t : conclusion.termList) {
			if (t.isVariable) {
				varList.add(t.variableName);
			} else {
				varList.add(t.value);

			}
		}
		return varList;
	}

	public void addSentence(Sentence s) {
		this.senList.add(s);
		if (s.isLiteral) {

			if (kbMap.containsKey(s.conclusion.predicateName)) {
				Rule rule = kbMap.get(s.conclusion.predicateName);
				if (s.conclusion.ifNegate) {
					rule.negLiterals.add(s.conclusion);

				} else {
					rule.posLiterals.add(s.conclusion);

				}

			} else {
				Rule rule = new Rule();
				if (s.conclusion.ifNegate) {
					rule.negLiterals.add(s.conclusion);

				} else {
					rule.posLiterals.add(s.conclusion);

				}

				kbMap.put(s.conclusion.predicateName, rule);
			}
		} else {

			if (kbMap.containsKey(s.conclusion.predicateName)) {
				Rule rule = kbMap.get(s.conclusion.predicateName);
				// rule.premises.put(s.pList);
				List<String> varList = getVariables(s.conclusion);

				VarMap varMap = new VarMap();
				varMap.ifNegate = s.conclusion.ifNegate;
				varMap.varList.addAll(varList);
				varMap.prList.addAll(s.pList);
				rule.premises.add(varMap);

			} else {
				Rule rule = new Rule();
				// rule.premises.add(s.pList);
				VarMap varMap = new VarMap();
				List<String> varList = getVariables(s.conclusion);
				varMap.ifNegate = s.conclusion.ifNegate;

				varMap.varList.addAll(varList);
				varMap.prList.addAll(s.pList);
				rule.premises.add(varMap);

				kbMap.put(s.conclusion.predicateName, rule);
			}
		}

		// TODO Auto-generated constructor stub
	}

	public boolean ask(Predicate qPredicate) {
		boolean result = false;
		// qPredicate = substitute(qPredicate);

		if (kbMap.containsKey(qPredicate.predicateName)) {
			result = checkLiterals(qPredicate);
			if (result) {
				return result;
			}
			result = dfsConjunctions(qPredicate);
		}

		return result;
	}

	private Predicate substitute(Predicate qPredicate, Map<String, String> l) {
		// *read variables from kbMap and update char array also
		// *change termlist in qPredicate
		for (Term term : qPredicate.termList) {
			if (term.isVariable) {
				if (l.containsKey(term.variableName)
						&& !l.get(term.variableName).equals("")) {

					term.value = l.get(term.variableName);
				}
			} else {

			}
		}
		for (Term term : qPredicate.termList) {
			if (term.value.length() < 1) {
				qPredicate.isUnified = false;
				break;
			}
			qPredicate.isUnified = true;

		}

		// TODO Auto-generated method stub
		return qPredicate;
	}

	private boolean checkLiterals(Predicate qPredicate) {
		boolean result = false;
		int res = 0;
		Rule rule = kbMap.get(qPredicate.predicateName);
		if (null != rule) {
			if (null != rule.posLiterals) {
				for (Predicate p : rule.posLiterals) {
					res = equalPredicate(p, qPredicate);
					if (res == 1) {
						// break;
						return true;
					}
					if (res == -1) {
						return false;
					}
				}
			}
			if (null != rule.negLiterals) {

				for (Predicate p : rule.negLiterals) {
					res = equalPredicate(p, qPredicate);
					if (res == 1) {
						// break;
						return true;
					}
					if (res == -1) {
						return false;
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return result;
	}

	// DFS traversal of a tree is performed by the dfs() function
	public boolean dfsConjunctions(Predicate predicate) {

		// DFS uses Stack data structure
		Deque<Predicate> s = new LinkedList<Predicate>();
		Stack<VarMap> vMap = new Stack<VarMap>();
		Stack<VarMap> v2Map = new Stack<VarMap>();
		// Set<String> explored = new HashSet<String>();
		Hashtable<String, String> exploredTable = new Hashtable<String, String>();

		List<Map<String, String>> orgMap = getMatchCharArr(predicate);
		List<Map<String, String>> lisMap2 = new ArrayList<Map<String, String>>(
				orgMap);

		// #F(Bob) in explored
		exploredTable.put(predicate.predicateName,
				stringifyTerms(predicate.termList));
		List<VarMap> vList = matchVmapMain(
				kbMap.get(predicate.predicateName).premises, predicate);

		int i = 0;

		while (i < vList.size()) {
			// vMap.removeAllElements();
			s.clear();
			// s.push(predicate);

			vMap.clear();
			v2Map.clear();
			VarMap v = vList.get(i);
			vMap.add(v);
			// v2Map.add(v);
			vmaploop: while (!vMap.isEmpty()) {
				// exploredTable.clear();
				v = vMap.pop();
				boolean resultLocal = false;
				s.clear();
				s.addAll(v.prList);
				Predicate p = v.prList.get(0);
				lisMap2 = getMatchVarCharArr2(p, lisMap2);

				Set<Map<String, String>> set = new HashSet<Map<String, String>>(
						lisMap2);

				lmap: for (Map<String, String> l : set) {
					exploredTable.clear();

					if (s.isEmpty()) {
						s.addAll(v.prList);

					}
					v2map: do {
						if (!v2Map.isEmpty()) {
							VarMap v2 = v2Map.pop();
							// for (Predicate pp : v2.prList) {
							// s.push(pp);

							// }//
							s.addAll(v2.prList);
						}
						smap: while (!s.isEmpty()) {
							// lisMap = getMatchVarCharArr(p, lisMap);

							p = s.peek();
							Predicate r = substitute(p, l);
							resultLocal = checkLiterals(r);
							if (resultLocal) {
								Predicate toPop = s.peek();
								List<Predicate> remList = removeDuplicates(s,
										toPop);
								if (remList.size() > 0) {
									s.removeAll(remList);
								}
								// now
								// s.pop();

								if (s.isEmpty()) {
									System.out.println(l);
									return resultLocal;
								}
								continue smap;
							}

							if (existsExplored(r, exploredTable)) {
								resultLocal = false;
								s.clear();
								v2Map.clear();
								continue lmap;
								// break;
							}

							if (r.isUnified) {
								exploredTable.put(r.predicateName,
										stringifyTerms(r.termList));

								if (!resultLocal) {

									// s.clear();
									// lisMap2.remove(l);
									if (null != kbMap.get(r.predicateName)) {
										if (matchVmap(
												kbMap.get(r.predicateName).premises,
												r).size() <= 0) {

											Predicate toPop = s.peek();
											List<Predicate> remList = removeDuplicates(
													s, toPop);
											if (remList.size() > 0) {
												s.removeAll(remList);
											}
											// now
											// s.pop();

											// v2Map.clear();
											continue v2map;
										}

										else {

											Predicate toPop = s.peek();
											List<Predicate> remList = removeDuplicates(
													s, toPop);
											if (remList.size() > 0) {
												s.removeAll(remList);
											}
											// now
											// s.pop();
											// v2Map.clear();

											v2Map.addAll(matchVmap(
													kbMap.get(r.predicateName).premises,
													r));
											continue v2map;
										}
									}

								}
								// }
								// nestList.addAll();

							}

							else {
								if (containsExtraVar(p, lisMap2)) {
									if (null != kbMap.get(r.predicateName)) {
										if (matchVmap(
												kbMap.get(r.predicateName).premises,
												r).size() <= 0) {
											// s.pop();
											// v2Map.clear();

											MapChange mp = getMatchVarCharArr(
													p, lisMap2);
											if (mp.changeMap) {
												s.clear();
												v2Map.clear();
												lisMap2 = mp.lisMap2;

												vMap.add(v);
												continue vmaploop;
											} else {
												Predicate toPop = s.peek();
												List<Predicate> remList = removeDuplicates(
														s, toPop);
												if (remList.size() > 0) {
													s.removeAll(remList);
												}
												// now
												// s.pop();

												continue v2map;
											}
										}

										else {

											Predicate toPop = s.peek();
											List<Predicate> remList = removeDuplicates(
													s, toPop);
											if (remList.size() > 0) {
												s.removeAll(remList);
											}
											// now
											// s.pop();

											// s.clear();
											// to check
											// v2Map.clear();
											v2Map.addAll(matchVmap(
													kbMap.get(r.predicateName).premises,
													r));
											continue v2map;
										}

									}
								}
								// nestList.addAll();

							}
						}
					} while (!v2Map.isEmpty());

				}
				if (!resultLocal
						&& (s.isEmpty() || vMap.isEmpty() || lisMap2.size() <= 0)) {

					i++;
					if (s.isEmpty()) {
						continue vmaploop;

					}
					if (vMap.isEmpty()) {

					}
					// vMap.clear();
				}
				if (resultLocal && s.isEmpty()) {
					return true;
				}
			}
		}

		System.gc();
		return false;
	}

	private boolean containsExtraVar(Predicate p,
			List<Map<String, String>> lisMap) {
		for (Term t : p.termList) {
			for (Map<String, String> m : lisMap) {
				if (!m.containsKey(t.variableName))
					return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	private List<VarMap> matchVmap(List<VarMap> premises2, Predicate predicate) {
		List<VarMap> premises = new ArrayList<VarMap>(premises2);
		List<VarMap> vMap = new ArrayList<VarMap>();
		if (null != premises) {
			for (VarMap vp : premises) {
				VarMap v = new VarMap();
				v = (VarMap) vp.clone();
				Map<String, String> mp = new HashMap<String, String>();
				if (v.ifNegate == predicate.ifNegate) {

					// v.varList.clear();
					for (Term t : predicate.termList) {
						if (!t.variableName.equals("")) {
							mp.put(v.varList.get(predicate.termList.indexOf(t)),
									t.variableName);
						} else if (t.variableName.equals("")) {
							mp.put(v.varList.get(predicate.termList.indexOf(t)),
									t.value);

						}
					}

				/*	for (Predicate p : v.prList) {
						for (Term t : p.termList) {

							for (Entry m : mp.entrySet()) {
								if (m.getValue().equals(t.variableName)) {
									if (m.getKey().equals(t.variableName)) {

									} else {
										t.variableName = t.variableName + "@";
									}

								}

							}
						}
					}*/
					for (Predicate p : v.prList) {
						for (Term t : p.termList) {
							if (mp.containsValue(t.variableName)) {
								t.variableName = mp.get(t.variableName);
								if (java.lang.Character
										.isUpperCase(t.variableName.charAt(0))) {
									t.value = t.variableName;
								} // if(t.variableName.charAt(0).)
							}

						}
					}
					for (Predicate p : v.prList) {
						for (Term t : p.termList) {
							if (mp.containsKey(t.variableName)) {
								t.variableName = mp.get(t.variableName);
								if (java.lang.Character
										.isUpperCase(t.variableName.charAt(0))) {
									t.value = t.variableName;
								} // if(t.variableName.charAt(0).)
							}

						}
					}
					vMap.add(v);
				}
			}
		}
		// TODO Auto-generated method stub
		return vMap;
	}

	private MapChange getMatchVarCharArr(Predicate p,
			List<Map<String, String>> lisMap2) {
		MapChange mp = new MapChange();
		List<Predicate> posLiterals = new ArrayList<Predicate>();
		if (null != kbMap.get(p.predicateName)
				&& null != kbMap.get(p.predicateName).posLiterals) {
			posLiterals = kbMap.get(p.predicateName).posLiterals;
		}
		List<Predicate> matchLiterals = new ArrayList<Predicate>();
		List<Map<String, String>> lismap = new ArrayList<Map<String, String>>();
		for (Predicate predicate : posLiterals) {
			Predicate mLiteral = null;
			if (p.termList.size() == predicate.termList.size()) {
				int i = 0;
				for (Term term : predicate.termList) {
					if (p.termList.get(i).value.equals("")) {
						if (null == mLiteral) {
							mLiteral = new Predicate(p);
							Term term2 = new Term(term);
							term2.variableName = p.termList.get(i).variableName;
							mLiteral.termList.add(term2);
						} else {
							Term term2 = new Term(term);
							term2.variableName = p.termList.get(i).variableName;
							mLiteral.termList.add(term2);
						}

					} else if (p.termList.get(i).value.equals(term.value)) {
						{
							if (null == mLiteral) {
								mLiteral = new Predicate(p);
								Term term2 = new Term(term);
								term2.variableName = p.termList.get(i).variableName;
								mLiteral.termList.add(term2);

							} else {
								Term term2 = new Term(term);
								term2.variableName = p.termList.get(i).variableName;
								mLiteral.termList.add(term2);

							}
						}
					}

					i++;
				}
			}
			if (null != mLiteral) {
				if (mLiteral.termList.size() == p.termList.size()) {
					matchLiterals.add(mLiteral);
				}
			}

		}
		if (matchLiterals.size() == 0) {
			mp.lisMap2 = lisMap2;
			mp.changeMap = false;
			return mp;
		}
		for (Predicate mpr : matchLiterals) {
			for (Map<String, String> map2 : lisMap2) {
				Map<String, String> map = new HashMap<String, String>(map2);
				for (Term term : mpr.termList) {

					if (map.containsKey(term.variableName)
							&& (map.get(term.variableName).equals(""))) {
						map.put(term.variableName, term.value);

					} else if (!map.containsKey(term.variableName)) {
						if (!term.variableName.equals(""))
							map.put(term.variableName, term.value);
						else
							map.put(term.value, term.value);

					}

				}

				lismap.add(map);

			}
		}

		// lismap

		mp.lisMap2 = lismap;
		mp.changeMap = true;
		return mp;

	}

	private boolean existsExplored(Predicate p,
			Hashtable<String, String> exploredTable) {
		if (exploredTable.containsKey(p.predicateName)) {
			String pr = exploredTable.get(p.predicateName);
			if (pr.equals(stringifyTerms(p.termList))) {
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	private String stringifyTerms(List<Term> termList) {

		String s = null;
		for (int i = 0; i < termList.size(); i++) {
			Term t = termList.get(i);
			if (i == 0) {
				s = "(" + t.value;
			} else {
				s = s + "," + t.value;
			}
		}
		// TODO Auto-generated method stub
		return s + ")";
	}

	int equalPredicate(Predicate p1, Predicate p2) {
		int result = 0;
		if (!p1.predicateName.equals(p2.predicateName)) {
			return 0;
		}
		boolean eqTerm = false;
		if (p1.termList.size() == p2.termList.size()) {
			eqTerm = true;

			for (int i = 0; i < p1.termList.size(); i++) {
				if (!equalTerm(p1.termList.get(i), p2.termList.get(i))) {
					eqTerm = false;
				}
			}
			if (!eqTerm) {
				return 0;
			} else {
				result = 1;
			}
			if (result == 1 && (p1.ifNegate != p2.ifNegate)) {
				result = -1;
			}
		}

		return result;
	}

	boolean equalTerm(Term one, Term two) {
		if (one.value.equals(""))
			return false;
		if (one.value.equals(two.value))
			return true;
		return false;

	}

	List<Map<String, String>> getMatchCharArr(Predicate predicate) {
		List<Map<String, String>> listCharArr = new ArrayList<Map<String, String>>();

		Rule r = kbMap.get(predicate.predicateName);
		for (VarMap var : r.premises) {
			if (var.ifNegate == predicate.ifNegate) {
				int i = 0;

				Map<String, String> charArr = new HashMap<String, String>();

				for (Term t : predicate.termList) {

					charArr.put(var.varList.get(i), t.value);

					i++;
				}
				listCharArr.add(charArr);
			}
			// } else {
			// #some constants and some variables in the sentence
			// #half substituted sentences
			// }

		}
		// *compute variables to be assigned using kbMap and merge main
		// charArray

		return listCharArr;
	}

	private List<Map<String, String>> getMatchVarCharArr2(Predicate p,
			List<Map<String, String>> lisMap2) {

		Predicate r = p;
		boolean breakLoop = true;
		HashSet<String> hs = new HashSet<String>();

		while (breakLoop && !hs.contains(r.predicateName)) {
			MapChange mp = new MapChange();
			mp = getMatchVarCharArr(r, lisMap2);
			if (!mp.lisMap2.isEmpty()) {
				lisMap2 = mp.lisMap2;
			} else {
				break;
			}
			hs.add(r.predicateName);
			if (null != kbMap.get(r.predicateName)) {
				if (matchVmap(kbMap.get(r.predicateName).premises, r).size() <= 0) {
					break;
				}

				r = matchVmap(kbMap.get(r.predicateName).premises, r).get(0).prList
						.get(0);
			} else {
				break;
			}
		}
		return lisMap2;
	}

	private List<VarMap> matchVmapMain(List<VarMap> premises,
			Predicate predicate) {
		List<VarMap> vMap = new ArrayList<VarMap>();

		for (VarMap v : premises) {
			if (v.ifNegate == predicate.ifNegate) {
				vMap.add(v);
			}
		}
		// TODO Auto-generated method stub
		return vMap;
	}

	// List<Predicate> posLiterals = kbMap.get(p.predicateName).posLiterals;
	List<Predicate> removeDuplicates(Deque<Predicate> s, Predicate p) {
		List<Predicate> remList = new ArrayList<Predicate>();

		Iterator<Predicate> it = s.iterator();
		while (it.hasNext()) {
			Predicate r = (Predicate) it.next();
			if (equalPredicate2(r, p)) {
				remList.add(r);

			}
		}
		return remList;
	}

	private boolean equalPredicate2(Predicate r, Predicate p) {
		// TODO Auto-generated method stub
		boolean result = false;
		if (!r.predicateName.equals(p.predicateName)) {
			return false;
		}
		boolean eqTerm = false;
		if (r.termList.size() == p.termList.size()) {
			eqTerm = true;

			for (int i = 0; i < r.termList.size(); i++) {
				if (!equalTermNew(r.termList.get(i), p.termList.get(i))) {
					eqTerm = false;
				}
			}
			if (!eqTerm) {
				return false;
			} else {
				result = true;
			}
			if (result == true && (p.ifNegate != r.ifNegate)) {
				result = false;
			}
		}

		return result;
	}

	private boolean equalTermNew(Term term, Term term2) {
		// TODO Auto-generated method stub
		if (term.value.equals(term.value)
				&& (term.isConstant | term.variableName
						.equals(term2.variableName)))
			return true;
		return false;
		// return false;
	}
}
