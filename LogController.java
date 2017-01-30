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
	private static File warehouseXml;
	private static File fieldXml;
	private static File userXml;
	private static Document warehouseLog;
	private static Document fieldLog;
	private static Document userLog;
	private static NodeList warehouseChildNodes;
	private static NodeList fieldsChildNodes;
	private static NodeList userChildNodes;
	private static ArrayList<ArrayList<String>> warehouseLogList;
	private static ArrayList<ArrayList<String>> fieldLogList;
	private static ArrayList<ArrayList<String>> userLogList;
	private DocumentBuilder docBuilder;

	LogController(){
		xmlInit();
		warehouseLogList = convertXmldocIntoStringList(warehouseChildNodes);
		fieldLogList = convertXmldocIntoStringList(fieldsChildNodes);
		userLogList = convertXmldocIntoStringList(userChildNodes);
	}

    /*ログの取得*/
	public ArrayList<String> getWarehouseLog(String nodeName){
		if(nodeName.equals("drinks")){
			return warehouseLogList.get(0);
		}else if(nodeName.equals("seeds")){
			return warehouseLogList.get(1);
		}else if(nodeName.equals("callots")){
			return warehouseLogList.get(2);
		}
		return null;
	}
    //enum
	public ArrayList<String> getFieldLog(int fieldPos){
		return fieldLogList.get(fieldPos-1);
	}
	public ArrayList<String> getUserLog(String nodeName){
		if(nodeName.equals("money")){
			return userLogList.get(0);
		}
		return null;
	}

    /*ログのセット*/
    public void setWarehouseLog(ArrayList<ArrayList<String>> newLog){
        warehouseLogList = newLog;
    }
    public void setFieldLog(ArrayList<ArrayList<String>> newLog){
        fieldLogList = newLog; 
    }
    public void setUserLog(ArrayList<ArrayList<String>> newLog){
        userLogList = newLog;
    } 


    /*ログの更新*/
	public boolean updateWarehouseLog(){
		ArrayList<String> outStringList;
		NodeList outNodeList;

		int j=0;
		for(int i=0; i<warehouseChildNodes.getLength(); i++){
			Node nc = warehouseChildNodes.item(i);
			if(nc.getNodeType()==Node.ELEMENT_NODE){
				outStringList = warehouseLogList.get(j);
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

		write(warehouseXml,warehouseLog);
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
	public boolean updateUserLog(){
		ArrayList<String> outStringList;
		NodeList outNodeList;

		int j=0;
		for(int i=0; i<userChildNodes.getLength(); i++){
			Node nc = userChildNodes.item(i);
			if(nc.getNodeType()==Node.ELEMENT_NODE){
				outStringList = userLogList.get(j);
                NodeList ncChildList = nc.getChildNodes();
                int n = 0;
                for(int k=0; k<ncChildList.getLength(); k++){
                    Node ng = ncChildList.item(k);
                    if(ng.getNodeType()==Node.ELEMENT_NODE){
                        ng.getFirstChild().setNodeValue(outStringList.get(n));
                        System.out.println("uselog: "+ng.getTextContent());
                        n++;
                    }else if(ng.getNodeType()==Node.TEXT_NODE){
                        if(!ng.getNodeValue().equals(null) && !ng.getNodeValue().equals("\n")){
                            ng.setNodeValue(outStringList.get(n));
                            System.out.println("uselog: "+ng.getTextContent());
                            n++;
                        }
                    }  
                }
				j++;
			}
		}
		write(userXml,userLog);

		return true;
	}

    /*xmlの内容を文字列のリストに変換*/
	private ArrayList<ArrayList<String>> convertXmldocIntoStringList(NodeList rootChildNodes){
		ArrayList<ArrayList<String>> logList = new ArrayList<ArrayList<String>>();
		for(int i=0; i<rootChildNodes.getLength(); i++){
			Node nc = rootChildNodes.item(i);
			ArrayList<String> sub = new ArrayList<String>();
			if(nc.getNodeType()==Node.ELEMENT_NODE){

				if(nc.hasChildNodes()){
					NodeList gChildNodes = nc.getChildNodes();
					for(int j=0; j<gChildNodes.getLength(); j++){
						Node ng = gChildNodes.item(j);

						if(ng.hasChildNodes()){
							NodeList ggChildNodes = ng.getChildNodes();
							for(int k=0; k<ggChildNodes.getLength(); k++){
								Node ngg = ggChildNodes.item(k);
								if(canReadValue(ngg)){
									sub.add(ngg.getTextContent());
								}
							}
						}else{
							if(canReadValue(ng)){
								sub.add(ng.getTextContent());
							}
						}
					}
				}else{
					if(canReadValue(nc)){
						sub.add(nc.getTextContent());
					}
				}
				logList.add(sub);

			}
		}
		return logList;
	}


	private boolean canReadValue(Node theNode){
		if(theNode.getNodeType()==Node.ELEMENT_NODE || theNode.getNodeType()==Node.TEXT_NODE){
			if(!theNode.getTextContent().equals("¥n") && !theNode.getTextContent().equals(null)){
				return true;
			}
		}
		return false;
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
		warehouseXml = new File("warehouseLog.xml");
		if(!warehouseXml.exists()){
			try{
			warehouseXml.createNewFile();
			setDefoElem(warehouseXml,LogType.WAREHOUSE);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			warehouseLog = loadLog(warehouseXml);
		}
		root = warehouseLog.getDocumentElement();
		warehouseChildNodes = root.getChildNodes();

        //畑xml
		fieldXml = new File("fieldLog.xml");
		if(!fieldXml.exists()){
			try{
				fieldXml.createNewFile();
				setDefoElem(fieldXml,LogType.FIELD);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			fieldLog = loadLog(fieldXml);
		}
		root = fieldLog.getDocumentElement();
		fieldsChildNodes = root.getChildNodes();

        //ユーザxml
		userXml = new File("userLog.xml");
		if(!userXml.exists()){
			try{
				userXml.createNewFile();
				setDefoElem(userXml,LogType.USER);
			}catch(IOException e){
				e.printStackTrace();
			}
		}else{
			userLog = loadLog(userXml);
		}
		root = userLog.getDocumentElement();
		userChildNodes = root.getChildNodes();
	}


    /*初期xml生成*/
	private void setDefoElem(File logFile, LogType logType){
		Element root=null;
		switch(logType){
			case WAREHOUSE:
				warehouseLog = docBuilder.newDocument(); 

				root = warehouseLog.createElement("warehouse"); 
				warehouseLog.appendChild(root);

                //drinks要素
				Element drinks = warehouseLog.createElement("drinks");
				root.appendChild(drinks);
				for(DrinkType type: DrinkType.values()){
					Element drink = warehouseLog.createElement("drink");
					drink.setAttribute("name",type.name());
					drink.appendChild(warehouseLog.createTextNode("0"));
					drinks.appendChild(drink);
				}

                //seeds要素
				Element seeds = warehouseLog.createElement("seeds");
				seeds.appendChild(warehouseLog.createTextNode("0"));
				root.appendChild(seeds);

                //callots要素
				Element callots = warehouseLog.createElement("callots");
				root.appendChild(callots);
				for(CallotColor color: CallotColor.values()){
					Element callot= warehouseLog.createElement("callot");
					callot.setAttribute("color",color.name());
					callot.appendChild(warehouseLog.createTextNode("0"));
					callots.appendChild(callot);
				}

				write(logFile,warehouseLog);
				break;

			case FIELD:
				fieldLog = docBuilder.newDocument();

				root = fieldLog.createElement("fields"); 
				fieldLog.appendChild(root);

				for(int i=1; i<7; i++){

                    //畑要素
					Element field = fieldLog.createElement("field");
					field.setAttribute("position",i+"");
					root.appendChild(field);

                    //畑の水分要素
					Element moisture = fieldLog.createElement("moisture");
					moisture.appendChild(fieldLog.createTextNode("0"));
					field.appendChild(moisture);

                    //畑の養分要素
					Element nutrition = fieldLog.createElement("nutrition");
					nutrition.appendChild(fieldLog.createTextNode("0"));
					field.appendChild(nutrition);

                    //callot要素
					Element callot= fieldLog.createElement("callot");

					Element gainedMoisture = fieldLog.createElement("gainedmoisture"); 
					gainedMoisture.appendChild(fieldLog.createTextNode("0"));
					callot.appendChild(gainedMoisture);

					Element gainedNutrition = fieldLog.createElement("gainednutrition");
					gainedNutrition.appendChild(fieldLog.createTextNode("0"));
					callot.appendChild(gainedNutrition);

					Element time = fieldLog.createElement("time");
					time.appendChild(fieldLog.createTextNode("0"));
					callot.appendChild(time);

					field.appendChild(callot);
				}

				write(logFile,fieldLog);
				break;

		    case USER:
				userLog = docBuilder.newDocument(); 

				root = userLog.createElement("user"); 
				userLog.appendChild(root);
				Element money = userLog.createElement("money");
				money.appendChild(userLog.createTextNode("0"));
				root.appendChild(money);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                String nowDateText = sdf.format(new Date());
                String[] nowDate = nowDateText.split("-"); 

                Element date = userLog.createElement("date");
                Element year = userLog.createElement("year");
                Element month = userLog.createElement("month");
                Element day = userLog.createElement("day");
                Element hour = userLog.createElement("hour");
                Element minute = userLog.createElement("minute");
				year.appendChild(userLog.createTextNode(nowDate[0]));
				month.appendChild(userLog.createTextNode(nowDate[1]));
				day.appendChild(userLog.createTextNode(nowDate[2]));
				hour.appendChild(userLog.createTextNode(nowDate[3]));
				minute.appendChild(userLog.createTextNode(nowDate[4]));
				date.appendChild(year);
				date.appendChild(month);
				date.appendChild(day);
				date.appendChild(hour);
				date.appendChild(minute);
				root.appendChild(date);

				write(logFile,userLog);
				break;

			default:
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

