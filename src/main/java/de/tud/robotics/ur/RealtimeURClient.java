package de.tud.robotics.ur;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.igormaznitsa.jbbp.exceptions.JBBPParsingException;
import com.igormaznitsa.jbbp.model.JBBPFieldArrayByte;
import com.igormaznitsa.jbbp.model.JBBPFieldInt;
import com.igormaznitsa.jbbp.model.JBBPFieldStruct;

import de.tud.robotics.ur.api.Joint;
import de.tud.robotics.ur.client.JointMode;
import de.tud.robotics.ur.client.data.RealtimeCartesianData;
import de.tud.robotics.ur.client.data.RealtimeJointData;
import de.tud.robotics.ur.client.data.RealtimeRobotModeData;
import de.tud.robotics.ur.client.data.RealtimeSingleJointData;
import de.tud.robotics.ur.parser.JBBPFieldDouble;
import de.tud.robotics.ur.parser.URPackageJBBPParserHelper;


public class RealtimeURClient extends CommonURClient {

	protected static final Logger LOG = Logger.getLogger(RealtimeURClient.class.getSimpleName());
	
	private static final int realtimePort = 30003;

	// for realtime client
	private RealtimeJointData rtjd;
	private RealtimeSingleJointData[] rtsjd;
	private RealtimeCartesianData rtcd;
	private RealtimeRobotModeData rtrmd;
	private JBBPFieldStruct realtimeMessage;
	
	public RealtimeURClient(String host) {
		super(host,realtimePort);
	}

	@Override
	protected void parse(InputStream in) throws IOException, ParsingException {
			try {
				messageHeader = URPackageJBBPParserHelper.getRealtimeMessageParser().parse(in);
			} catch (JBBPParsingException e) {
				LOG.log(Level.WARNING, "exception while parsing", e);
				parsingErrorCount++;
				if (parsingErrorCount > 1000)
					throw new ParsingException(" multiple parsing error occures", e);
				return;
			}
			parsingErrorCount = 0;
			try {
				length = messageHeader.findFieldForNameAndType("messagesize", JBBPFieldInt.class).getAsInt();
				if(length != 1060) {
					LOG.log(Level.WARNING, "invalid message length");
					return;
				}
				currentTime = System.currentTimeMillis();
				a = messageHeader.findFieldForType(JBBPFieldArrayByte.class).getArray();
				// TODO Remove
				/*for(int i = 0; i < a.length; i = i+8) {
					byte[] test = new byte[] {a[i],a[i+1],a[i+2],a[i+3],a[i+4],a[i+5],a[i+6],a[i+7]};
					System.out.println("test:"+Arrays.toString(test));
					double d = ByteBuffer.wrap(test).order(ByteOrder.BIG_ENDIAN ).getDouble();
					
					System.out.println(d);
					byte[] bytes = new byte[8];
				    ByteBuffer.wrap(bytes).putDouble(d);
				    System.out.println("bytes:"+Arrays.toString(bytes));
				}*/
				realtimeMessage = URPackageJBBPParserHelper.getRealtimePackageParser().parse(a);
				// parse Joint data
				rtjd = new RealtimeJointData();
				rtjd.setSender(getName());
				rtjd.setLastUpdated(currentTime);
				rtsjd = new RealtimeSingleJointData[6];
				for(int i= 0; i < rtsjd.length;i++) {
					rtsjd[i] = new RealtimeSingleJointData();
					rtsjd[i].setIactual((float)realtimeMessage.findFieldForNameAndType("Iactual"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setJointMode(realtimeMessage.findFieldForNameAndType("jointMode"+i,JBBPFieldDouble.class).getAsInt());
					rtsjd[i].setQactual(realtimeMessage.findFieldForNameAndType("qactual"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setQdactual(realtimeMessage.findFieldForNameAndType("qdactual"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setQtarget(realtimeMessage.findFieldForNameAndType("qtarget"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setVactual((float)realtimeMessage.findFieldForNameAndType("vactual"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setTmotor((float)realtimeMessage.findFieldForNameAndType("motorTemperature"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setIcontrol(realtimeMessage.findFieldForNameAndType("Icontrol"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setItarget(realtimeMessage.findFieldForNameAndType("Itarget"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setMtarget(realtimeMessage.findFieldForNameAndType("Mtarget"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setQddtarget(realtimeMessage.findFieldForNameAndType("qddtarget"+i,JBBPFieldDouble.class).getAsDouble());
					rtsjd[i].setQdtarget(realtimeMessage.findFieldForNameAndType("qdtarget"+i,JBBPFieldDouble.class).getAsDouble());
				}
				rtjd.setJoints(rtsjd);
				jointData = rtjd;
				// System.out.println(Arrays.toString(jointData.getJointPositionMessage().toArray()));
				if (!jointData.getJointData(Joint.BASE).getJointMode().equals(JointMode.JOINT_POWER_OFF_MODE)) {
					notifyListeners(jointData);
				}
				
				// parse cartesian Data
				rtcd = new RealtimeCartesianData();
				rtcd.setLastUpdated(currentTime);
				rtcd.setSender(getName());
				
				rtcd.setX(realtimeMessage.findFieldForNameAndType("tcpactualX",JBBPFieldDouble.class).getAsDouble());
				rtcd.setY(realtimeMessage.findFieldForNameAndType("tcpactualY",JBBPFieldDouble.class).getAsDouble());
				rtcd.setZ(realtimeMessage.findFieldForNameAndType("tcpactualZ",JBBPFieldDouble.class).getAsDouble());
				rtcd.setRx(realtimeMessage.findFieldForNameAndType("tcpactualRx",JBBPFieldDouble.class).getAsDouble());
				rtcd.setRy(realtimeMessage.findFieldForNameAndType("tcpactualRy",JBBPFieldDouble.class).getAsDouble());
				rtcd.setRz(realtimeMessage.findFieldForNameAndType("tcpactualRz",JBBPFieldDouble.class).getAsDouble());
				
				rtcd.setSpeedX(realtimeMessage.findFieldForNameAndType("tcpSpeedactualX",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedY(realtimeMessage.findFieldForNameAndType("tcpSpeedactualY",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedZ(realtimeMessage.findFieldForNameAndType("tcpSpeedactualZ",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedRz(realtimeMessage.findFieldForNameAndType("tcpSpeedactualRx",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedRy(realtimeMessage.findFieldForNameAndType("tcpSpeedactualRy",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedRz(realtimeMessage.findFieldForNameAndType("tcpSpeedactualRz",JBBPFieldDouble.class).getAsDouble());
				
				rtcd.setForceX(realtimeMessage.findFieldForNameAndType("tcpForceactualX",JBBPFieldDouble.class).getAsDouble());
				rtcd.setForceY(realtimeMessage.findFieldForNameAndType("tcpForceactualY",JBBPFieldDouble.class).getAsDouble());
				rtcd.setForceZ(realtimeMessage.findFieldForNameAndType("tcpForceactualZ",JBBPFieldDouble.class).getAsDouble());
				rtcd.setForceRx(realtimeMessage.findFieldForNameAndType("tcpForceactualRx",JBBPFieldDouble.class).getAsDouble());
				rtcd.setForceRy(realtimeMessage.findFieldForNameAndType("tcpForceactualRy",JBBPFieldDouble.class).getAsDouble());
				rtcd.setForceRz(realtimeMessage.findFieldForNameAndType("tcpForceactualRz",JBBPFieldDouble.class).getAsDouble());
				
				rtcd.setTargetX(realtimeMessage.findFieldForNameAndType("tcptargetX",JBBPFieldDouble.class).getAsDouble());
				rtcd.setTargetY(realtimeMessage.findFieldForNameAndType("tcptargetY",JBBPFieldDouble.class).getAsDouble());
				rtcd.setTargetZ(realtimeMessage.findFieldForNameAndType("tcptargetZ",JBBPFieldDouble.class).getAsDouble());
				rtcd.setTargetRx(realtimeMessage.findFieldForNameAndType("tcptargetRx",JBBPFieldDouble.class).getAsDouble());
				rtcd.setTargetRy(realtimeMessage.findFieldForNameAndType("tcptargetRy",JBBPFieldDouble.class).getAsDouble());
				rtcd.setTargetRz(realtimeMessage.findFieldForNameAndType("tcptargetRz",JBBPFieldDouble.class).getAsDouble());
				
				rtcd.setSpeedTargetX(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetX",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedTargetY(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetY",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedTargetZ(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetZ",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedTargetRx(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetRx",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedTargetRy(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetRy",JBBPFieldDouble.class).getAsDouble());
				rtcd.setSpeedTargetRz(realtimeMessage.findFieldForNameAndType("tcpSpeedtargetRz",JBBPFieldDouble.class).getAsDouble());
	
				cartesianData = rtcd;
				notifyListeners(cartesianData);
				
				// parse RobotMode Data
				// data are not complete dont parse them
				/*
				rtrmd = new RealtimeRobotModeData();
				rtrmd.setLastUpdated(currentTime);
				rtrmd.setSender(name);
				//rtrmd.setTimestamp(realtimeMessage.findFieldForNameAndType("time",JBBPFieldDouble.class).getAsDouble());
				rtrmd.setRobotMode(realtimeMessage.findFieldForNameAndType("robotMode",JBBPFieldDouble.class).getAsInt());
				rtrmd.setSpeedScaling(realtimeMessage.findFieldForNameAndType("speedScaling",JBBPFieldDouble.class).getAsDouble());
				if (!rtrmd.equals(robotModeData)) {
					robotModeData = rtrmd;
					notifyListeners(robotModeData);
				}*/
			} catch(Throwable e) {
				e.printStackTrace();
			}
		}



}
