import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import java.util.Date;
import java.text.SimpleDateFormat;
public class LogController {
    public static final int STORAGE = 1;
    public static final int FIELD = 2;
	private static File storageXml;
	private static File fieldXml;
	private static Document storageLog;
	private static Document fieldLog;
	private static NodeList storageChildNodes;
	private static NodeList fieldsChildNodes;
	private static ArrayList<ArrayList<String>> storageLogList;
	private static ArrayList<ArrayList<String>> fieldLogList;
	private DocumentBuilder docBuilder;

	LogController(){
		xmlInit();
		storageLogList = convertXmlIntoString(storageChildNodes);
		fieldLogList = convertXmlIntoString(fieldsChildNodes);
	}

    /*ログの取得*/
	public ArrayList<String> getStorageLog(String nodeName){
		if(nodeName.equals("drinks")){
			return storageLogList.get(0);
		}else if(nodeName.equals("seeds")){
			return storageLogList.get(1);
		}else if(nodeName.equals("carrots")){
			return storageLogList.get(2);
		}else if(nodeName.equals("money")){
            return storageLogList.get(3);
        }
		return null;
	}
	public ArrayList<String> getFieldLog(FieldPos pos){
		return fieldLogList.get(pos.ordinal());
	}
	public ArrayList<String> getFieldLog(String nodeName){
        if(nodeName.equals("date")){
            return fieldLogList.get(6);
        }
        return null;
	}

    /*ログのセット*/
    public void setStorageLog(ArrayList<ArrayList<String>> newLog){
        storageLogList = newLog;
    }
    public void setFieldLog(ArrayList<ArrayList<String>> newLog){
        fieldLogList = newLog; 
    }


    /*ログの更新*/
	public boolean updateStorageLog(){
		ArrayList<String> outStringList;
		NodeList outNodeList;

		int j=0;
		for(int i=0; i<storageChildNodes.getLength(); i++){
			Node nc = storageChildNodes.item(i);
			if(nc.getNodeType()==Node.ELEMENT_NODE){
				outStringList = storageLogList.get(j);
				j++;
				outNodeList = nc.getChildNodes();
				int n=0;
				for(int k=0; k<outNodeList.getLength(); k++){
					Node ng = outNodeList.item(k);
					if(ng.getNodeType()==Node.ELEMENT_NODE){
						ng.getFirstChild().setNodeValue(outStringList.get(n));
						System.out.print(ng.getTextContent()+" ");
						n++;
					}else if(ng.getNodeType()==Node.TEXT_NODE){
						if(!ng.getNodeValue().equals(null) && !ng.getNodeValue().equals("¥n")){
							ng.setNodeValue(outStringList.get(n));
							System.out.print(ng.getTextContent()+" ");
							n++;
						}
					}
				}
				System.out.print("¥n");
			}
		}

		write(storageXml,storageLog);
		return true;
	}
	public boolean updateFieldLog(){
		ArrayList<String> outStringList;
		NodeList outNodeList;

		int n=0;
		for(int i=0; i<fieldsChildNodes.getLength(); i++){
			Node nc = fieldsChildNodes.item(i);
			if(nc.getNodeType()==Node.ELEMENT_NODE){
				outNodeList = nc.getChildNodes();
				outStringList = fieldLogList.get(n);
				n++;
				int k=0;
				for(int j=0; j<outNodeList.getLength(); j++){
					Node ng = outNodeList.item(j);
					if(ng.getNodeType()==Node.ELEMENT_NODE){
						NodeList nggChildList = ng.getChildNodes();
						for(int m=0; m<nggChildList.getLength(); m++){
							Node ngg = nggChildList.item(m);
							if(ngg.getNodeType()==Node.ELEMENT_NODE){
								ngg.getFirstChild().setNodeValue(outStringList.get(k));
								System.out.print(ngg.getTextContent()+" ");
								k++;
							}else if(ngg.getNodeType()==Node.TEXT_NODE){
								if(!ngg.getNodeValue().equals(null) && !ngg.getNodeValue().equals("¥n")){
									ngg.setNodeValue(outStringList.get(k));
									System.out.print(ngg.getTextContent()+" ");
									k++;
								}
							}
						}
					}
				}
				System.out.print("¥n");
			}
		}
		write(fieldXml,fieldLog);
		return true;
	}

    //xmlドキュメント→ 文字列リストに変換
    private ArrayList<ArrayList<String>> convertXmlIntoString(NodeList rootChildNodes){
        ArrayList<ArrayList<String>> log = new ArrayList<ArrayList<String>>();
        int j = 0;
        for(int i=0; i<rootChildNodes.getLength(); i++){
            Node nc = rootChildNodes.item(i);
            if(nc.getNodeType() == Node.ELEMENT_NODE){
                j++;
                NodeList ncNodeList = nc.getChildNodes();
                ArrayList<String> sub = new ArrayList<String>();
                for(int k=0; k<ncNodeList.getLength(); k++){
                    Node ng = ncNodeList.item(k);
                    if(ng.getNodeType() == Node.ELEMENT_NODE){
                        NodeList ngNodeList = ng.getChildNodes();
                        for(int m=0; m<ngNodeList.getLength(); m++){
                            Node ngg = ngNodeList.item(m);
                            if(ngg.getNodeType() == Node.ELEMENT_NODE){
                                String text = ngg.getFirstChild().getNodeValue();
                                sub.add(text);
                            }else if(ngg.getNodeType() == Node.TEXT_NODE){
                                if(!ngg.getTextContent().equals("\n") && !ngg.getTextContent().equals(null)){
                                    String text = ngg.getNodeValue();
                                    sub.add(text);
                                }
                            }

                        }

                    }else if(ng.getNodeType() == Node.TEXT_NODE){
                        if(!ng.getTextContent().equals("\n") && !ng.getTextContent().equals(null)){
                            String text = ng.getNodeValue();
                            sub.add(text);
                        }
                    }
                }
                log.add(sub);
            }
        }
        return log;
    }


    /*xmlをメモリに展開*/
	private void xmlInit(){
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
		}catch(ParserConfigurationException e){
			e.printStackTrace();
		}

		Node root;

        //倉庫xml
		storageXml = new File("storageLog.xml");
		if(!storageXml.exists()){
			try{
			storageXml.createNewFile();
			setDefoElem(storageXml,STORAGE);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			storageLog = loadLog(storageXml);
		}
		root = storageLog.getDocumentElement();
		storageChildNodes = root.getChildNodes();

        //畑xml
		fieldXml = new File("fieldLog.xml");
		if(!fieldXml.exists()){
			try{
				fieldXml.createNewFile();
				setDefoElem(fieldXml,FIELD);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			fieldLog = loadLog(fieldXml);
		}
		root = fieldLog.getDocumentElement();
		fieldsChildNodes = root.getChildNodes();
	}


    /*初期xml生成*/
	private void setDefoElem(File logFile, int logType){
		Element root=null;
		switch(logType){
			case STORAGE:
				storageLog = docBuilder.newDocument(); 

				root = storageLog.createElement("storage"); 
				storageLog.appendChild(root);

                //drinks要素
				Element drinks = storageLog.createElement("drinks");
				root.appendChild(drinks);
				for(DrinkType type: DrinkType.values()){
                    if(type != DrinkType.NONE){
                        Element drink = storageLog.createElement("drink");
                        drink.setAttribute("name",type.name());
                        drink.appendChild(storageLog.createTextNode("0"));
                        drinks.appendChild(drink);
                    }
				}

                //seeds要素
				Element seeds = storageLog.createElement("seeds");
				seeds.appendChild(storageLog.createTextNode("0"));
				root.appendChild(seeds);

                //carrots要素
				Element carrots = storageLog.createElement("carrots");
				root.appendChild(carrots);
				for(CarrotType type: CarrotType.values()){
					Element carrot= storageLog.createElement("carrot");
					carrot.setAttribute("type",type.name());
					carrot.appendChild(storageLog.createTextNode("0"));
					carrots.appendChild(carrot);
				}

                //money要素
				Element money = storageLog.createElement("money");
				money.appendChild(storageLog.createTextNode("0"));
				root.appendChild(money);

				write(logFile,storageLog);
				break;

			case FIELD:
				fieldLog = docBuilder.newDocument();

				root = fieldLog.createElement("fields"); 
				fieldLog.appendChild(root);

                //各フィールド
				for(FieldPos pos: FieldPos.values()){

                    //畑要素
					Element field = fieldLog.createElement("field");
					field.setAttribute("position",pos.name());
					root.appendChild(field);

                    //飲み物要素
					Element drink = fieldLog.createElement("drink");
					drink.appendChild(fieldLog.createTextNode(DrinkType.NONE.name()));
					field.appendChild(drink);

                    //経過時間要素
					Element time = fieldLog.createElement("time");
					time.appendChild(fieldLog.createTextNode("0"));
					field.appendChild(time);

                    //carrot要素
					Element carrot= fieldLog.createElement("carrot");
                    for(DrinkType drinkType: DrinkType.values()){
                        if(drinkType != DrinkType.NONE){
                            Element gainedDrink = fieldLog.createElement("gaineddrink"); 
                            gainedDrink.setAttribute("type",drinkType.name());
                            gainedDrink.appendChild(fieldLog.createTextNode("0"));
                            carrot.appendChild(gainedDrink);
                        }
                    }
					field.appendChild(carrot);
				}

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String nowDateText = sdf.format(new Date());
                String[] nowDate = nowDateText.split("-"); 

                //日付(ゲーム終了時)
                Element date = fieldLog.createElement("date");
                Element year = fieldLog.createElement("year");
                Element month = fieldLog.createElement("month");
                Element day = fieldLog.createElement("day");
                Element hour = fieldLog.createElement("hour");
                Element minute = fieldLog.createElement("minute");
				year.appendChild(fieldLog.createTextNode(nowDate[0]));
				month.appendChild(fieldLog.createTextNode(nowDate[1]));
				day.appendChild(fieldLog.createTextNode(nowDate[2]));
				hour.appendChild(fieldLog.createTextNode(nowDate[3]));
				minute.appendChild(fieldLog.createTextNode(nowDate[4]));
				date.appendChild(year);
				date.appendChild(month);
				date.appendChild(day);
				date.appendChild(hour);
				date.appendChild(minute);
				root.appendChild(date);

				write(logFile,fieldLog);
				break;
		}
	}


    /*xmlを解析*/
	private Document loadLog(File logFile){
		Document xmlDoc=null;
		try{
            //xmlドキュメントに変換
			xmlDoc = docBuilder.parse(new FileInputStream(logFile));
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(SAXException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		return xmlDoc;
	}


    /*xmlファイルに書き出す*/
	public boolean write(File logFile, Document logDoc){

        //Transformerインスタンス生成
		Transformer tf=null;
		try{
			TransformerFactory tff = TransformerFactory.newInstance();
			tf = tff.newTransformer();
		}catch(TransformerConfigurationException e){
			e.printStackTrace();
			return false;
		}

        //Transformerの設定
		tf.setOutputProperty("indent","yes");
		tf.setOutputProperty("encoding","Shift_JIS");

        //xmlファイルへ出力
		try{
			tf.transform(new DOMSource(logDoc),new StreamResult(logFile));
		}catch(TransformerException e){
			e.printStackTrace();
			return false;
		}

		return true;
	}


    /*ノードの種類チェック*/
	private void printNodeType(Node node){
		short nodeType = node.getNodeType();
		if(nodeType==Node.ATTRIBUTE_NODE){
			System.out.println("attribute");
		}else if(nodeType==Node.ELEMENT_NODE){
			System.out.println("element");
		}else if(nodeType==Node.TEXT_NODE){
			System.out.println("text");
		}else{
			System.out.println("other");
		}
	}
}

