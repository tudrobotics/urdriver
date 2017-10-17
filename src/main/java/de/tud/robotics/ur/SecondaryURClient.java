package de.tud.robotics.ur;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Level;

import com.igormaznitsa.jbbp.exceptions.JBBPParsingException;
import com.igormaznitsa.jbbp.model.JBBPFieldArrayByte;
import com.igormaznitsa.jbbp.model.JBBPFieldInt;
import com.igormaznitsa.jbbp.model.JBBPFieldUByte;

import de.tud.robotics.ur.api.Joint;
import de.tud.robotics.ur.client.JointMode;
import de.tud.robotics.ur.client.RobotMessageType;
import de.tud.robotics.ur.client.RobotPackageType;
import de.tud.robotics.ur.client.data.AdditionalData;
import de.tud.robotics.ur.client.data.CartesianData;
import de.tud.robotics.ur.client.data.ConfigurationData;
import de.tud.robotics.ur.client.data.ForceModeData;
import de.tud.robotics.ur.client.data.JointData;
import de.tud.robotics.ur.client.data.KinematicsData;
import de.tud.robotics.ur.client.data.MasterboardData;
import de.tud.robotics.ur.client.data.RobotModeData;
import de.tud.robotics.ur.client.data.ToolData;
import de.tud.robotics.ur.parser.URPackageJBBPParserHelper;


public class SecondaryURClient extends CommonURClient {

	private static final int primaryPort = 30002;
	
	private static final int expectedPackageCount = 8;
	
	private double[] zero = new double[]{0.0,0.0,0.0,0.0,0.0,0.0};
	// data
	protected RobotModeData robotModeData;
	protected ToolData toolData;
	protected MasterboardData masterBoardData;
	protected KinematicsData kinematicsData;
	protected ConfigurationData configurationData;
	protected ForceModeData forceModeData;
	protected AdditionalData additionalData;

	
	// for normal client
	private RobotModeData rmd;
	private JointData jd;
	private ToolData td;
	private MasterboardData mbd;
	private CartesianData cd;
	private ForceModeData fmd;
	private AdditionalData ad;
	
	public SecondaryURClient(String host) {		
		super(host, primaryPort);
		robotModeData = new RobotModeData();
		jointData = new JointData();
		toolData = new ToolData();
		masterBoardData = new MasterboardData();
		cartesianData = new CartesianData();
		kinematicsData = new KinematicsData();
		configurationData = new ConfigurationData();
		forceModeData = new ForceModeData();
		additionalData = new AdditionalData();
	}


	@Override
	protected void parse(InputStream in) throws IOException, ParsingException {
		try {
			messageHeader = URPackageJBBPParserHelper.getMessageParser().parse(in);
		} catch (JBBPParsingException e) {
			parsingErrorCount++;
			if (parsingErrorCount > 1000)
				throw new ParsingException("multiple parsing error occures", e);
			return;
		}
		parsingErrorCount = 0;
		length = messageHeader.findFieldForNameAndType("packageLength", JBBPFieldInt.class).getAsInt();
		type = RobotMessageType.from(
				messageHeader.findFieldForNameAndType("packageType", JBBPFieldUByte.class).getAsInt());
		// parse only package 636 
		if (length != 636 || !type.equals(RobotMessageType.ROBOT_STATE))
			return;
		// expecting 8 Packages
		currentTime = System.currentTimeMillis();
		for (int i = 0; i < expectedPackageCount; i++) {
			p = URPackageJBBPParserHelper.getPackagepParser().parse(in);
			packageLength = URPackageJBBPParserHelper.getPackageLength(p);
			packageType = URPackageJBBPParserHelper.getRobotPackageType(p);
			// LOG.log(Level.INFO, "receiving package
			// "+packageType+"("+packageLength+" bytes)");
			a = p.findFieldForType(JBBPFieldArrayByte.class).getArray();
			// System.out.println(Arrays.toString(a));
			switch (packageType) {
				case ROBOT_MODE_DATA:
					if (packageType.equals(RobotModeData.packageType)
							&& packageLength == RobotModeData.packageLength) {
						if (isOutdated(RobotPackageType.ROBOT_MODE_DATA, robotModeData, currentTime)) {
							rmd = URPackageJBBPParserHelper.getRobotModeDataParser().parse(a).mapTo(
									RobotModeData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							rmd.setSender(getName());
							// System.out.println(new Date()+"update ROBOT_MODE_DATA");
							robotModeData = rmd;
							notifyListeners(robotModeData);

						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case JOINT_DATA:
					if (packageType.equals(JointData.packageType)
							&& packageLength == JointData.packageLength) {
						if (isOutdated(RobotPackageType.JOINT_DATA, jointData, currentTime)) {
							jd = URPackageJBBPParserHelper.getJointDataParser().parse(a).mapTo(
									JointData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							jd.setLastUpdated(currentTime);
							jd.setSender(getName());
							// System.out.println(String.format("%.4f",
							// (jd.getJointData(Joint.BASE).getQtarget()-jd.getJointData(Joint.BASE).getQactual()))+"
							// "+String.format("%.4f",
							// (jd.getJointData(Joint.ELBOW).getQtarget()-jd.getJointData(Joint.ELBOW).getQactual()))+"
							// "+String.format("%.4f",
							// (jd.getJointData(Joint.SHOULDER).getQtarget()-jd.getJointData(Joint.SHOULDER).getQactual()))+"
							// "+String.format("%.4f",
							// (jd.getJointData(Joint.WRIST1).getQtarget()-jd.getJointData(Joint.WRIST1).getQactual()))+"
							// "+String.format("%.4f",
							// (jd.getJointData(Joint.WRIST2).getQtarget()-jd.getJointData(Joint.WRIST2).getQactual()))+"
							// "+String.format("%.4f",
							// (jd.getJointData(Joint.WRIST3).getQtarget()-jd.getJointData(Joint.WRIST3).getQactual())));
							// System.out.println(Arrays.toString(jointData.toJointMessage().toArray()));
							if (!Arrays.equals(jd.jointPositionArray(), zero)) {
								jointData = jd;
								// System.out.println(Arrays.toString(jointData.getJointPositionMessage().toArray()));
								if (!jointData.getJointData(Joint.BASE).getJointMode()
										.equals(JointMode.JOINT_POWER_OFF_MODE))
									notifyListeners(jointData);
							}

						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case TOOL_DATA:
					if (packageType.equals(ToolData.packageType)
							&& packageLength == ToolData.packageLength) {
						if (isOutdated(RobotPackageType.TOOL_DATA, toolData, currentTime)) {
							td = URPackageJBBPParserHelper.getToolDataParser().parse(a).mapTo(
									ToolData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							td.setLastUpdated(currentTime);
							td.setSender(getName());
							
							toolData = td;
							notifyListeners(toolData);
						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case MASTERBOARD_DATA:
					if (packageType.equals(MasterboardData.packageType)
							&& packageLength == MasterboardData.packageLength) {
						if (isOutdated(RobotPackageType.MASTERBOARD_DATA, masterBoardData, currentTime)) {
							mbd = URPackageJBBPParserHelper.getMasterboardDataParser().parse(a).mapTo(
									MasterboardData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							mbd.setLastUpdated(currentTime);
							mbd.setSender(getName());
							
							masterBoardData = mbd;
							notifyListeners(masterBoardData);
						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case CARTESIAN_DATA:
					if (packageType.equals(CartesianData.packageType)
							&& packageLength == CartesianData.packageLength) {
						if (isOutdated(RobotPackageType.CARTESIAN_DATA, cartesianData, currentTime)) {
							cd = URPackageJBBPParserHelper.getCartesianDataParser().parse(a).mapTo(
									CartesianData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							cd.setLastUpdated(currentTime);
							cd.setSender(getName());
							// System.out.println(cartesianData.getX()+"
							// "+cartesianData.getY()+"
							// "+cartesianData.getZ());
							// System.out.println(cartesianData.getRx()+"
							// "+cartesianData.getRy()+"
							// "+cartesianData.getRz());
							
							cartesianData = cd;
							notifyListeners(cartesianData);
						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case KINEMATICS_DATA:
					if (packageType.equals(KinematicsData.packageType)
							&& packageLength == KinematicsData.packageLength) {
						if (isOutdated(RobotPackageType.KINEMATICS_DATA, kinematicsData, currentTime)) {
							// skip this package
							// URPackageJBBPParserHelper.getKinematicDataParser().parse(a);
							LOG.log(Level.INFO, "KinematicsData not implemented");
						}
					}
					break;
				case CONFIGURATION_DATA:
					if (packageType.equals(ConfigurationData.packageType)
							&& packageLength == ConfigurationData.packageLength) {
						if (isOutdated(RobotPackageType.CONFIGURATION_DATA, configurationData,
								currentTime)) {
							// skip this package
							// URPackageJBBPParserHelper.getConfigurationDataParser().parse(a);
							LOG.log(Level.INFO, "ConfigurationData not implemented");
						}

					}
					break;
				case FORCE_MODE_DATA:
					if (packageType.equals(ForceModeData.packageType)
							&& packageLength == ForceModeData.packageLength) {
						if (isOutdated(RobotPackageType.FORCE_MODE_DATA, forceModeData, currentTime)) {
							fmd = URPackageJBBPParserHelper.getForceModeDataParser().parse(a).mapTo(
									ForceModeData.class,
									URPackageJBBPParserHelper.getFloatAndDoubleFieldProcessor());
							fmd.setLastUpdated(currentTime);
							fmd.setSender(getName());
							
							forceModeData = fmd;
							notifyListeners(forceModeData);
						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case ADDITIONAL_INFO:
					if (packageType.equals(AdditionalData.packageType)
							&& packageLength == AdditionalData.packageLength) {
						if (isOutdated(RobotPackageType.ADDITIONAL_INFO, additionalData, currentTime)) {
							ad = URPackageJBBPParserHelper.getAdditionalDataParser().parse(a)
									.mapTo(AdditionalData.class);
							ad.setLastUpdated(currentTime);
							ad.setSender(getName());
								
							additionalData = ad;
							notifyListeners(additionalData);
						}
					} else {
						LOG.log(Level.INFO,
								"skipping defect " + packageType + "(" + packageLength + " bytes)");
					}
					break;
				case CALIBRATION_DATA:
					// skip this package
					// URPackageJBBPParserHelper.getCalibrationDataParser().parse(a);
					break;
				default:
					break;
				}
		}
		
	}

}
