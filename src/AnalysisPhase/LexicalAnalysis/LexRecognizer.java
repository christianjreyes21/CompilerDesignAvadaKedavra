package AnalysisPhase.LexicalAnalysis;

public class LexRecognizer {

	// char charSet[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	// 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
	// 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
	// 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
	// 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ';' , '=', '+',
	// '-', '*', '/', '\\' , '&', '<', '>' , '(' , ')', '{' , '}', '[', ']' ,
	// '#' , ':' ,'\'', '.' , ',' , '%', '^', '!', '@', '~' , '$', '\"', ' ',
	// '\t' , '\n'};
	/*
	 * static char Integer[] = {'I', 'N', 'T', 'E', 'G', 'E', 'R'}; static char
	 * Decimal[] = {'D', 'E', 'C', 'I', 'M', 'A', 'L'}; static char Character[]
	 * = {'C', 'H', 'A', 'R', 'A', 'C', 'T', 'E', 'R'}; static char String[] =
	 * {'S', 'T', 'R', 'I', 'N', 'G'}; static char Boolean[] = {'B', 'O', 'O',
	 * 'L', 'E', 'A', 'N'}; static char Switch[] = {'S', 'W', 'I', 'T', 'C',
	 * 'H'}; static char Case[] = {'C', 'A', 'S', 'E'}; static char Stop[] =
	 * {'S', 'T', 'O', 'P'}; static char Default[] = {'D', 'E', 'F', 'A', 'U',
	 * 'L', 'T'}; static char For[] = {'F', 'O', 'R'}; static char OP[] = {'O',
	 * 'U', 'T', 'P', 'U', 'T', '.', 'P', 'R', 'I', 'N', 'T'}; static char OPl[]
	 * = {'O', 'U', 'T', 'P', 'U', 'T', '.', 'P', 'R', 'I', 'N', 'T', 'L', 'N'};
	 * static char IG[] = {'I', 'N', 'P', 'U', 'T', '.', 'G', 'E', 'T'};
	 */
	// static String Keywords[] =
	// {"%SWITCH","%CASE","%STOP","%DEFAULT","%FOR","%OUTPUT.PRINT","%OUTPUT.PRINTLN","%INPUT.GET",
	// "%TRUE", "%FALSE"};
	// static String reservedWords[] =
	// {"#INTEGER","#DECIMAL","#CHARACTER","#STRING","#BOOLEAN"};

	public static Token operator(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;

		if (str.length() == 1) {
			token.tokenName = "ERROR";
		} else if (str.length() == 4) {
			if (str.charAt(1) == 'M') {
				if (str.charAt(2) == 'O') {
					if (str.charAt(3) == 'D') {
						token.tokenName = "ARITH_OP_MOD";
					}
				}
			} else if (str.charAt(1) == 'D') {
				if (str.charAt(2) == 'I') {
					if (str.charAt(3) == 'V') {
						token.tokenName = "ARITH_OP_DIV";
					}
				}
			} else if (str.charAt(0) == '&') {
				if (str.charAt(1) == 'A') {
					if (str.charAt(2) == 'N') {
						if (str.charAt(3) == 'D') {
							token.tokenName = "LOG_OP_AND";
						}
					}
				} else if (str.charAt(1) == 'N') {
					if (str.charAt(2) == 'O') {
						if (str.charAt(3) == 'T') {
							token.tokenName = "LOG_OP_NOT";
						}
					}
				}
			}
		}
		// if (str.length() == 1 || str.length() == 2)
		if (str.length() == 1 || str.length() == 2) {
			if (str.charAt(0) == '+') {
				token.tokenName = "ARITH_OP_ADD";
				if ((str.length() == 2)
						&& ((str.charAt(0) == '+') && (str.charAt(1) == '+'))) {
					token.tokenName = "ARITH_OP_INCRE";
				}
			} else if (str.charAt(0) == '-') {
				token.tokenName = "ARITH_OP_SUBT";
				if ((str.length() == 2)
						&& ((str.charAt(0) == '-') && (str.charAt(1) == '-'))) {
					token.tokenName = "ARITH_OP_DECRE";
				}
			} else if (str.charAt(0) == '*') {
				token.tokenName = "ARITH_OP_MULT";
			} else if (str.charAt(0) == '/') {
				token.tokenName = "ARITH_OP_DIV";
			} else if (str.charAt(0) == '^') {
				token.tokenName = "ARITH_OP_EXPON";
			} else if (str.charAt(0) == '=') {
				token.tokenName = "ARITH_OP_ASSIGN";
			} else if (str.charAt(0) == '=' && str.charAt(1) == '=') {
				token.tokenName = "REL_OP_EQUALTO";
			} else if (str.charAt(0) == '<') {
				token.tokenName = "REL_OP_LESS";
				if ((str.length() == 2)
						&& ((str.charAt(0) == '<') && (str.charAt(1) == '='))) {
					token.tokenName = "REL_OP_LESSEQUAL";
				}
			} else if (str.charAt(0) == '>') {
				token.tokenName = "REL_OP_GREATER";
				if ((str.length() == 2)
						&& ((str.charAt(0) == '>') && (str.charAt(1) == '='))) {
					token.tokenName = "REL_OP_GREATEREQUAL";
				}
			} else if (str.charAt(0) == '!') {
				token.tokenName = "ERROR";
				if ((str.length() == 2) && (str.charAt(0) == '!')
						&& (str.charAt(1) == '=')) {
					token.tokenName = "REL_OP_NOTEQUALTO";
				}
			}
		}
		if (str.length() == 3) {
			if (str.charAt(1) == 'O') {
				if (str.charAt(2) == 'R') {
					token.tokenName = "OR_OP";
				}
			}
		}
		return token;
	}

	public static Token noise(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;

		if (str.length() == 3 || str.length() == 5) {
			if (str.charAt(1) == 'G') {
				if (str.charAt(2) == 'O') {
					token.tokenName = "NOISE_GO";
				}
			} else if (str.charAt(1) == 'G') {
				if (str.charAt(2) == 'O')
					if (str.charAt(3) == 'T') {
						if (str.charAt(4) == 'O') {
							token.tokenName = "NOISE_GOTO";
						}
					}
			}
		}
		return token;
	}

	public static Token keyword(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		// Boolean bool = false;
		// if (str.charAt(0) == '%') {
		if (str.length() == 5) {
			if (str.charAt(1) == 'T') {
				if (str.charAt(2) == 'R') {
					if (str.charAt(3) == 'U') {
						if (str.charAt(4) == 'E') {
							token.tokenName = "KEYWORD_BOOLEAN_TRUE";
						}
					}
				}
			}
		}
		if (str.length() == 6) {
			if (str.charAt(1) == 'F') {
				if (str.charAt(2) == 'A') {
					if (str.charAt(3) == 'L') {
						if (str.charAt(4) == 'S') {
							if (str.charAt(5) == 'E') {
								token.tokenName = "KEYWORD_BOOLEAN_FALSE";
							}
						}
					}
				}
			}
		}
		if (str.length() == 7) {
			if (str.charAt(1) == 'S') {
				if (str.charAt(2) == 'W') {
					if (str.charAt(3) == 'I') {
						if (str.charAt(4) == 'T') {
							if (str.charAt(5) == 'C') {
								if (str.charAt(6) == 'H') {
									token.tokenName = "KEYWORD_SWITCH";
								}
							}
						}
					}
				}
			}
		}
		if (str.length() == 5) {
			if (str.charAt(1) == 'C') {
				if (str.charAt(2) == 'A') {
					if (str.charAt(3) == 'S') {
						if (str.charAt(4) == 'E') {
							token.tokenName = "KEYWORD_STOP";
						}
					}
				}
			}
			if (str.charAt(1) == 'S') {
				if (str.charAt(2) == 'T') {
					if (str.charAt(3) == 'O') {
						if (str.charAt(4) == 'P') {
							token.tokenName = "KEYWORD_STOP";
						}
					}
				}
			}
		}
		if (str.length() == 8) {
			if (str.charAt(1) == 'D') {
				if (str.charAt(2) == 'E') {
					if (str.charAt(3) == 'F') {
						if (str.charAt(4) == 'A') {
							if (str.charAt(5) == 'U') {
								if (str.charAt(6) == 'L') {
									if (str.charAt(7) == 'T') {
										token.tokenName = "KEYWORD_DEFAULT";
									}
								}
							}
						}
					}
				}
			}
		}
		if (str.length() == 4) {
			if (str.charAt(1) == 'F') {
				if (str.charAt(2) == 'O') {
					if (str.charAt(3) == 'R') {
						token.tokenName = "KEYWORD_FOR";
					}
				}
			}
		}
		if (str.length() == 13 || str.length() == 15) {
			if (str.charAt(1) == 'O') {
				if (str.charAt(2) == 'U') {
					if (str.charAt(3) == 'T') {
						if (str.charAt(4) == 'P') {
							if (str.charAt(5) == 'U') {
								if (str.charAt(6) == 'T') {
									if (str.charAt(7) == '.') {
										if (str.charAt(8) == 'P') {
											if (str.charAt(9) == 'R') {
												if (str.charAt(10) == 'I') {
													if (str.charAt(11) == 'N') {
														if (str.charAt(12) == 'T') {
															token.tokenName = "KEYWORD_OUTPUT.PRINT";
															if ((str.length() == 14)
																	&& ((str.charAt(12) == 'T') && (str
																			.charAt(13) == 'L'))) {
																if (str.charAt(14) == 'N') {
																	token.tokenName = "KEYWORD_OUTPUT.PRINTLN";
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (str.length() == 10) {
			if (str.charAt(1) == 'I') {
				if (str.charAt(2) == 'N') {
					if (str.charAt(3) == 'P') {
						if (str.charAt(4) == 'U') {
							if (str.charAt(5) == 'T') {
								if (str.charAt(6) == '.') {
									if (str.charAt(7) == 'G') {
										if (str.charAt(8) == 'E') {
											if (str.charAt(9) == 'T') {
												token.tokenName = "KEYWORD_INPUT.GET";
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		// }
		// BOOLEAN , SWITCH , CASE , STOP , DEFAULT , FOR , OUTPUT.PRINT ,
		// OUTPRINT.PRINTLN , INPUT.GET
		/*
		 * for (int i = 0; i < Keywords.length; i++) { for (int j=0;
		 * j<Keywords[i].length() && j<str.length() - 1; j++) {
		 * //System.out.println(str.charAt(j) + " " + Keywords[i].charAt(j) +
		 * j+str.length() + valid); if(str.charAt(j) != Keywords[i].charAt(j)) {
		 * if((str.charAt(1) == 'T' && str.charAt(2) == 'R' && str.charAt(3) ==
		 * 'U' && str.charAt(4) == 'E') || (str.charAt(1) == 'F' &&
		 * str.charAt(2) == 'A' && str.charAt(3) == 'L' && str.charAt(4) == 'S'
		 * && str.charAt(5) == 'E')) { token.tokenName = "BOOLEAN"; } break;
		 * 
		 * } else { if(j==str.length()-2) { valid=true; } } } }
		 */
		/*
		 * if (bool = false){ token.tokenName = "ERROR"; }
		 */
		return token;
	}

	public static Token reservedWord(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;

		// if (str.charAt(0) == '#') {
		
		if (str.length() == 8) {
			if (str.charAt(1) == 'B') {
				if (str.charAt(2) == 'O') {
					if (str.charAt(3) == 'O') {
						if (str.charAt(4) == 'L') {
							if (str.charAt(5) == 'E') {
								if (str.charAt(6) == 'A') {
									if (str.charAt(7) == 'N') {
										token.tokenName = "RESERVEDWORD_BOOLEAN";
									}
								}
							}
						}
					}
				}
			}
			if (str.charAt(1) == 'I') {
				if (str.charAt(2) == 'N') {
					if (str.charAt(3) == 'T') {
						if (str.charAt(4) == 'E') {
							if (str.charAt(5) == 'G') {
								if (str.charAt(6) == 'E') {
									if (str.charAt(7) == 'R') {
										token.tokenName = "RESERVEDWORD_INTEGER";
									}
								}
							}
						}
					}
				}
			}
			if (str.charAt(1) == 'D') {
				if (str.charAt(2) == 'E') {
					if (str.charAt(3) == 'C') {
						if (str.charAt(4) == 'I') {
							if (str.charAt(5) == 'M') {
								if (str.charAt(6) == 'A') {
									if (str.charAt(7) == 'L') {
										token.tokenName = "RESERVEDWORD_DECIMAL";
									}
								}
							}
						}
					}
				}
			}
		}
		if (str.length() == 7) {
			if (str.charAt(1) == 'S') {
				if (str.charAt(2) == 'T') {
					if (str.charAt(3) == 'R') {
						if (str.charAt(4) == 'I') {
							if (str.charAt(5) == 'N') {
								if (str.charAt(6) == 'G') {
									token.tokenName = "RESERVEDWORD_STRING";
								}
							}
						}
					}
				}
			}
		}
		if (str.length() == 10) {
			if (str.charAt(1) == 'C') {
				if (str.charAt(2) == 'H') {
					if (str.charAt(3) == 'A') {
						if (str.charAt(4) == 'R') {
							if (str.charAt(5) == 'A') {
								if (str.charAt(6) == 'C') {
									if (str.charAt(7) == 'T') {
										if (str.charAt(8) == 'E') {
											if (str.charAt(9) == 'R') {
												token.tokenName = "RESERVEDWORD_CHARACTER";
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// }
		/*
		 * if (bool == false) { token.tokenName = "ERROR"; }
		 */
		return token;
	}

	public static Token identifier(String str, int line) {
		Token token = new Token();
		token.tokenName = "IDENT";
		token.tokenAttribute = str;
		boolean valid = true;
		token.lineNumber = line;

		// check if the symbol preceeding @ is a part of the alphabet
		if (!(str.charAt(1) == 'A' || str.charAt(1) == 'B'
				|| str.charAt(1) == 'C' || str.charAt(1) == 'D'
				|| str.charAt(1) == 'E' || str.charAt(1) == 'F'
				|| str.charAt(1) == 'G' || str.charAt(1) == 'H'
				|| str.charAt(1) == 'I' || str.charAt(1) == 'J'
				|| str.charAt(1) == 'K' || str.charAt(1) == 'L'
				|| str.charAt(1) == 'M' || str.charAt(1) == 'N'
				|| str.charAt(1) == 'O' || str.charAt(1) == 'P'
				|| str.charAt(1) == 'Q' || str.charAt(1) == 'R'
				|| str.charAt(1) == 'S' || str.charAt(1) == 'T'
				|| str.charAt(1) == 'U' || str.charAt(1) == 'V'
				|| str.charAt(1) == 'W' || str.charAt(1) == 'X'
				|| str.charAt(1) == 'Y' || str.charAt(1) == 'Z'
				|| str.charAt(1) == 'a' || str.charAt(1) == 'b'
				|| str.charAt(1) == 'c' || str.charAt(1) == 'd'
				|| str.charAt(1) == 'e' || str.charAt(1) == 'f'
				|| str.charAt(1) == 'g' || str.charAt(1) == 'h'
				|| str.charAt(1) == 'i' || str.charAt(1) == 'j'
				|| str.charAt(1) == 'k' || str.charAt(1) == 'l'
				|| str.charAt(1) == 'm' || str.charAt(1) == 'n'
				|| str.charAt(1) == 'o' || str.charAt(1) == 'p'
				|| str.charAt(1) == 'q' || str.charAt(1) == 'r'
				|| str.charAt(1) == 's' || str.charAt(1) == 't'
				|| str.charAt(1) == 'u' || str.charAt(1) == 'v'
				|| str.charAt(1) == 'w' || str.charAt(1) == 'x'
				|| str.charAt(1) == 'y' || str.charAt(1) == 'z'))
			valid = false;
		else { // check if the rest is composed of valid characters
			for (int i = 2; i < str.length(); i++)
				if (!(str.charAt(1) == 'A' || str.charAt(1) == 'B'
						|| str.charAt(1) == 'C' || str.charAt(1) == 'D'
						|| str.charAt(1) == 'E' || str.charAt(1) == 'F'
						|| str.charAt(1) == 'G' || str.charAt(1) == 'H'
						|| str.charAt(1) == 'I' || str.charAt(1) == 'J'
						|| str.charAt(1) == 'K' || str.charAt(1) == 'L'
						|| str.charAt(1) == 'M' || str.charAt(1) == 'N'
						|| str.charAt(1) == 'O' || str.charAt(1) == 'P'
						|| str.charAt(1) == 'Q' || str.charAt(1) == 'R'
						|| str.charAt(1) == 'S' || str.charAt(1) == 'T'
						|| str.charAt(1) == 'U' || str.charAt(1) == 'V'
						|| str.charAt(1) == 'W' || str.charAt(1) == 'X'
						|| str.charAt(1) == 'Y' || str.charAt(1) == 'Z'
						|| str.charAt(1) == 'a' || str.charAt(1) == 'b'
						|| str.charAt(1) == 'c' || str.charAt(1) == 'd'
						|| str.charAt(1) == 'e' || str.charAt(1) == 'f'
						|| str.charAt(1) == 'g' || str.charAt(1) == 'h'
						|| str.charAt(1) == 'i' || str.charAt(1) == 'j'
						|| str.charAt(1) == 'k' || str.charAt(1) == 'l'
						|| str.charAt(1) == 'm' || str.charAt(1) == 'n'
						|| str.charAt(1) == 'o' || str.charAt(1) == 'p'
						|| str.charAt(1) == 'q' || str.charAt(1) == 'r'
						|| str.charAt(1) == 's' || str.charAt(1) == 't'
						|| str.charAt(1) == 'u' || str.charAt(1) == 'v'
						|| str.charAt(1) == 'w' || str.charAt(1) == 'x'
						|| str.charAt(1) == 'y' || str.charAt(1) == 'z'
						|| str.charAt(1) == '0' || str.charAt(1) == '1'
						|| str.charAt(1) == '2' || str.charAt(1) == '3'
						|| str.charAt(1) == '4' || str.charAt(1) == '5'
						|| str.charAt(1) == '6' || str.charAt(1) == '7'
						|| str.charAt(1) == '8' || str.charAt(1) == '9'
						|| str.charAt(1) == '-' || str.charAt(1) == '_')) {
					valid = false;
					break;
				}
		}

		if (!valid)
			token.tokenName = "ERROR";

		return token;
	}

	public static Token indent(String str, int line) {
		Token token = new Token();
		token.tokenName = "INDENT";
		token.tokenAttribute = str;
		token.lineNumber = line;

		return token;
	}

	public static Token comment(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		if (str.charAt(0) == ':' && str.charAt(1) == '/') {
			token.tokenName = "COMMENT_SINGLE";
		} else if ((str.charAt(0) == '(' && str.charAt(1) == ':')
				&& (str.charAt(str.length() - 1) == ')' && str.charAt(str
						.length() - 2) == ':')) {
			token.tokenName = "COMMENT_MULTI";
		}
		return token;
	}

	public static Token delim(String str, int line) {
		Token token = new Token();
		token.tokenName = "DELIM";
		token.tokenAttribute = str;
		token.lineNumber = line;
		
		  if (str.length() != 1) {
			  token.tokenName = "ERROR";
		  }
		  
		return token;
	}

	public static Token number(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;

		int dotCounter = 0;
		// start sa 1 kasi pwedeng negative. Yung pagcheck ng negative nasa Lex
		// na function, kasama na sya dun sa if statement. Pag nagstart yung
		// character sa - 0 1 2 3 4 5 6 7 8 9, bale, pag nagstart sa -
		// understood na yun na negative tas dito
		// checheck nalang yung remaining symbols kung number ba talaga sila
		for (int i = 0; i < str.length(); i++) {
			if (!(str.charAt(i) == '0' || str.charAt(i) == '1'
					|| str.charAt(i) == '2' || str.charAt(i) == '3'
					|| str.charAt(i) == '4' || str.charAt(i) == '5'
					|| str.charAt(i) == '6' || str.charAt(i) == '7'
					|| str.charAt(i) == '8' || str.charAt(i) == '9' || str
						.charAt(i) == '.'))
				break;
			else {
				if (str.charAt(i) == '.')
					dotCounter++;

				if (dotCounter == 0)
					token.tokenName = "INTEGER";
				else if (dotCounter == 1)
					token.tokenName = "DECIMAL";
			}
		}

		if ((str.charAt(0) == '-' && str.length() == 1)
				|| (str.charAt(0) == '-' && str.charAt(1) == '.')) {
			token.tokenName = "ERROR";
		}

		return token;
	}

	public static Token blank(String str, int line) {
		Token token = new Token();
		token.tokenName = "SPACE";
		token.tokenAttribute = "";
		token.lineNumber = line;

		if (str.charAt(0) == ' ')
			token.tokenName = "SPACE";
		else if (str.charAt(0) == '\n')
			token.tokenName = "NEWLINE";

		return token;
	}

	public static Token charOrString(String str, int line) {
		Token token = new Token();
		token.tokenName = "ERROR";
		token.tokenAttribute = str;
		token.lineNumber = line;
		//boolean valid = true;

		if ((str.length() == 1)
				&& (str.charAt(0) == 'A' || str.charAt(0) == 'B'
						|| str.charAt(0) == 'C' || str.charAt(0) == 'D'
						|| str.charAt(0) == 'E' || str.charAt(0) == 'F'
						|| str.charAt(0) == 'G' || str.charAt(0) == 'H'
						|| str.charAt(0) == 'I' || str.charAt(0) == 'J'
						|| str.charAt(0) == 'K' || str.charAt(0) == 'L'
						|| str.charAt(0) == 'M' || str.charAt(0) == 'N'
						|| str.charAt(0) == 'O' || str.charAt(0) == 'P'
						|| str.charAt(0) == 'Q' || str.charAt(0) == 'R'
						|| str.charAt(0) == 'S' || str.charAt(0) == 'T'
						|| str.charAt(0) == 'U' || str.charAt(0) == 'V'
						|| str.charAt(0) == 'W' || str.charAt(0) == 'X'
						|| str.charAt(0) == 'Y' || str.charAt(0) == 'Z'
						|| str.charAt(0) == 'a' || str.charAt(0) == 'b'
						|| str.charAt(0) == 'c' || str.charAt(0) == 'd'
						|| str.charAt(0) == 'e' || str.charAt(0) == 'f'
						|| str.charAt(0) == 'g' || str.charAt(0) == 'h'
						|| str.charAt(0) == 'i' || str.charAt(0) == 'j'
						|| str.charAt(0) == 'k' || str.charAt(0) == 'l'
						|| str.charAt(0) == 'm' || str.charAt(0) == 'n'
						|| str.charAt(0) == 'o' || str.charAt(0) == 'p'
						|| str.charAt(0) == 'q' || str.charAt(0) == 'r'
						|| str.charAt(0) == 's' || str.charAt(0) == 't'
						|| str.charAt(0) == 'u' || str.charAt(0) == 'v'
						|| str.charAt(0) == 'w' || str.charAt(0) == 'x'
						|| str.charAt(0) == 'y' || str.charAt(0) == 'z')){
			token.tokenName = "CHARACTER";
		}
		else {
			for (int i = 0; i < str.length(); i++) {
				if (!(str.charAt(i) == 'A' || str.charAt(i) == 'B'
						|| str.charAt(i) == 'C' || str.charAt(i) == 'D'
						|| str.charAt(i) == 'E' || str.charAt(i) == 'F'
						|| str.charAt(i) == 'G' || str.charAt(i) == 'H'
						|| str.charAt(i) == 'I' || str.charAt(i) == 'J'
						|| str.charAt(i) == 'K' || str.charAt(i) == 'L'
						|| str.charAt(i) == 'M' || str.charAt(i) == 'N'
						|| str.charAt(i) == 'O' || str.charAt(i) == 'P'
						|| str.charAt(i) == 'Q' || str.charAt(i) == 'R'
						|| str.charAt(i) == 'S' || str.charAt(i) == 'T'
						|| str.charAt(i) == 'U' || str.charAt(i) == 'V'
						|| str.charAt(i) == 'W' || str.charAt(i) == 'X'
						|| str.charAt(i) == 'Y' || str.charAt(i) == 'Z'
						|| str.charAt(i) == 'a' || str.charAt(i) == 'b'
						|| str.charAt(i) == 'c' || str.charAt(i) == 'd'
						|| str.charAt(i) == 'e' || str.charAt(i) == 'f'
						|| str.charAt(i) == 'g' || str.charAt(i) == 'h'
						|| str.charAt(i) == 'i' || str.charAt(i) == 'j'
						|| str.charAt(i) == 'k' || str.charAt(i) == 'l'
						|| str.charAt(i) == 'm' || str.charAt(i) == 'n'
						|| str.charAt(i) == 'o' || str.charAt(i) == 'p'
						|| str.charAt(i) == 'q' || str.charAt(i) == 'r'
						|| str.charAt(i) == 's' || str.charAt(i) == 't'
						|| str.charAt(i) == 'u' || str.charAt(i) == 'v'
						|| str.charAt(i) == 'w' || str.charAt(i) == 'x'
						|| str.charAt(i) == 'y' || str.charAt(i) == 'z')) {
					//valid = false;
					break;
				}
			}

			/*if (valid)
				token.tokenName = "STRING";*/
		}
		return token;
	}
}
