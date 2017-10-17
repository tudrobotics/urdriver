package de.tud.robotics.ur.parser;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.model.JBBPFieldInt;
import com.igormaznitsa.jbbp.model.JBBPFieldStruct;
import com.igormaznitsa.jbbp.model.JBBPFieldUByte;

import de.tud.robotics.ur.client.RobotMessageType;
import de.tud.robotics.ur.client.RobotPackageType;

public final class URPackageJBBPParserHelper {

	private static final JBBPFloatAndDoubleTypeProcessor floatAndDoubleTypeProcessor = new JBBPFloatAndDoubleTypeProcessor();
	private static final JBBPFloatAndDoubleFieldProcessor floatAndDoubleFieldProcessor = new JBBPFloatAndDoubleFieldProcessor();
	
	private static final JBBPParser messageParser= JBBPParser.prepare(
			"int packageLength;"
		    +"ubyte packageType;"
	);
	private static final JBBPParser packageParser= JBBPParser.prepare(
            "int packageLength;"
            +"ubyte packageType;"
            +"byte [packageLength-5] data;"
	);
	private static final JBBPParser robotModeDataParser = JBBPParser.prepare(
            "long timestamp; "
            +"bool physicalRobotConnected; "		
            +"bool realRobotEnabled; "		
            +"bool robotPowerOn; "		
            +"bool emergencyStopped; "		
            +"bool protectiveStopped; "		
            +"bool programRunning; "		
            +"bool programPaused; "
            +"ubyte robotMode; "
            +"ubyte controlMode; "
            +"double targetSpeedFraction; "
            +"double speedScaling;"
            +"double targetSpeedFractionLimit;"
          ,floatAndDoubleTypeProcessor);
	private static final JBBPParser jointDataParser = JBBPParser.prepare(
			"joints[6] {"
            +"double qactual;"			
            +"double qtarget;"			
            +"double qdactual;"			
            +"float Iactual;"			
            +"float Vactual;"			
            +"float Tmotor;"			
            +"float Tmicro;"			
            +"ubyte jointMode;"
            +"}"
            ,floatAndDoubleTypeProcessor);
	private static final JBBPParser toolDataParser = JBBPParser.prepare(
            "ubyte analogInputRange2;"			
            +"ubyte analogInputRange3;"			
            +"double analogInput2;"			
            +"double analogInput3;"			
            +"float	toolVoltage48V;"			
            +"ubyte toolOutputVoltage;"			
            +"float toolCurrent;"			
            +"float	toolTemperature;"
            +"ubyte toolMode;"
       ,floatAndDoubleTypeProcessor);
	private static final JBBPParser masterBoardDataParser = JBBPParser.prepare(
            "int digitalInputBits;"			
            +"int digitalOutputBits;"			
            +"byte analogInputRange0;"			
            +"byte analogInputRange1;"			
            +"double analogInput0;"			
            +"double analogInput1;"			
            +"byte analogOutputDomain0;"			
            +"byte	analogOutputDomain1;"
            +"double analogOutput0;"
            +"double AnalogOutput1;"
            +"float masterboardTemperature;"
            +"float robotVoltage48V;"
            +"float robotCurrent;"
            +"float masterIOCurrent;"
            +"ubyte safetymode;"
            +"ubyte inReducedMode;"
            +"byte euromap67Installed;"
           /* +"int euromapInputBits0;" //If Euromap67 is installed
            +"int euromapInputBits1;" //If Euromap67 is installed
            +"float euromapVoltage;" // If Euromap67 is installed
            +"float euromapCurrent;" //If Euromap67 is installed*/
            +"int unknown;"
            +"ubyte operationalModeSelectorInput;"
            +"ubyte threePositionEnablingDeviceInput;", 
            floatAndDoubleTypeProcessor);
           
	private static final JBBPParser cartesianDataParser = JBBPParser.prepare(
            "double x;"			
            +"double y;"			
            +"double z;"			
            +"double rx;"			
            +"double ry;"			
            +"double rz;"			
            +"double tcpOffsetX;"			
            +"double tcpOffsetY;"
            +"double tcpOffsetZ;"
            +"double tcpOffsetRX;"
            +"double tcpOffsetRY;"
            +"double tcpOffsetRZ;"
     , floatAndDoubleTypeProcessor);
	// do nothing
	private static final JBBPParser kinematicDataParser = JBBPParser.prepare(
            "skip:225;"
			);
	private static final JBBPParser configurationDataParser = JBBPParser.prepare(
            "double jointMinLimit;"			
            +"double jointMaxLimit;"			
            +"double JointMaxSpeed;"			
            +"double jointMaxAcceleration;"			
            +"double v_jointDefault;"			
            +"double a_jointDefault;"			
            +"double v_toolDefault;"			
            +"double a_toolDefault;"
            +"double eq_radius;"
            +"double dh_a;"
            +"double dh_d;"
            +"double dh_alpha;"
            +"double dh_theta;"
            +"int masterboardVersion;"
            +"int controllerBoxType;"
            +"int robotType;"
            +"int robotSubType;"
      , floatAndDoubleTypeProcessor);
	private static final JBBPParser forceModeDataParser = JBBPParser.prepare(
            "double x;"			
            +"double y;"			
            +"double z;"			
            +"double rx;"			
            +"double ry;"			
            +"double tz;"			
            +"double robotDexterity;"			
     , floatAndDoubleTypeProcessor);
	private static final JBBPParser additionalDataParser = JBBPParser.prepare(
            "bool teachButtonPressed;"			
            +"bool teachButtonEnabled;"			
            +"bool ioEnabledFreedrive;"			
    );
	// do nothing
	private static final JBBPParser calibrationDataParser = JBBPParser.prepare(
            "skip:53;");
	private static final JBBPParser realtimeMessageParser = JBBPParser.prepare(
			"int messagesize;"
			+"byte[messagesize-4] data;"
	);
	private static final JBBPParser realtimePackageParser = JBBPParser.prepare(
			"double time;"
			+"double qtarget0;"
			+"double qtarget1;"
			+"double qtarget2;"
			+"double qtarget3;"
			+"double qtarget4;"
			+"double qtarget5;"
			+"double qdtarget0;"
			+"double qdtarget1;"
			+"double qdtarget2;"
			+"double qdtarget3;"
			+"double qdtarget4;"
			+"double qdtarget5;"
			+"double qddtarget0;"
			+"double qddtarget1;"
			+"double qddtarget2;"
			+"double qddtarget3;"
			+"double qddtarget4;"
			+"double qddtarget5;"
			+"double Itarget0;"
			+"double Itarget1;"
			+"double Itarget2;"
			+"double Itarget3;"
			+"double Itarget4;"
			+"double Itarget5;"
			+"double Mtarget0;"
			+"double Mtarget1;"
			+"double Mtarget2;"
			+"double Mtarget3;"
			+"double Mtarget4;"
			+"double Mtarget5;"
			+"double qactual0;"
			+"double qactual1;"
			+"double qactual2;"
			+"double qactual3;"
			+"double qactual4;"
			+"double qactual5;"
			+"double qdactual0;"
			+"double qdactual1;"
			+"double qdactual2;"
			+"double qdactual3;"
			+"double qdactual4;"
			+"double qdactual5;"
			+"double Iactual0;"
			+"double Iactual1;"
			+"double Iactual2;"
			+"double Iactual3;"
			+"double Iactual4;"
			+"double Iactual5;"
			+"double Icontrol0;"
			+"double Icontrol1;"
			+"double Icontrol2;"
			+"double Icontrol3;"
			+"double Icontrol4;"
			+"double Icontrol5;"
			+"double tcpactualX;"
			+"double tcpactualY;"
			+"double tcpactualZ;"
			+"double tcpactualRx;"
			+"double tcpactualRy;"
			+"double tcpactualRz;"
			+"double tcpSpeedactualX;"
			+"double tcpSpeedactualY;"
			+"double tcpSpeedactualZ;"
			+"double tcpSpeedactualRx;"
			+"double tcpSpeedactualRy;"
			+"double tcpSpeedactualRz;"
			+"double tcpForceactualX;"
			+"double tcpForceactualY;"
			+"double tcpForceactualZ;"
			+"double tcpForceactualRx;"
			+"double tcpForceactualRy;"
			+"double tcpForceactualRz;"
			+"double tcptargetX;"
			+"double tcptargetY;"
			+"double tcptargetZ;"
			+"double tcptargetRx;"
			+"double tcptargetRy;"
			+"double tcptargetRz;"
			+"double tcpSpeedtargetX;"
			+"double tcpSpeedtargetY;"
			+"double tcpSpeedtargetZ;"
			+"double tcpSpeedtargetRx;"
			+"double tcpSpeedtargetRy;"
			+"double tcpSpeedtargetRz;"
			+"double digitalInputBits;"
			+"double motorTemperature0;"
			+"double motorTemperature1;"
			+"double motorTemperature2;"
			+"double motorTemperature3;"
			+"double motorTemperature4;"
			+"double motorTemperature5;"
			+"double controllerTimer;"
			+"double testValue;"
			+"double robotMode;"
			+"double jointMode0;"
			+"double jointMode1;"
			+"double jointMode2;"
			+"double jointMode3;"
			+"double jointMode4;"
			+"double jointMode5;"
			+"double safetyMode;"
			+"skip:48;"
			+"double toolAccelerometerValueX;"
			+"double toolAccelerometerValueY;"
			+"double toolAccelerometerValueZ;"
			+"skip:48;"
			+"double speedScaling;"
			+"double linearMomentumNorm;"
			+"skip:16;"
			+"double Vmain;"
			+"double Vrobot;"
			+"double Irobot;"
			+"double Vactual0;"
			+"double Vactual1;"
			+"double Vactual2;"
			+"double Vactual3;"
			+"double Vactual4;"
			+"double Vactual5;"
			+"double digitalOutputs;"
			+"double programState;"				
     , floatAndDoubleTypeProcessor);
	
	
	private URPackageJBBPParserHelper() {	
	}
	
	public static JBBPFloatAndDoubleTypeProcessor getFloatAndDoubleTypeProcessor() {
		return floatAndDoubleTypeProcessor;
	}


	public static JBBPFloatAndDoubleFieldProcessor getFloatAndDoubleFieldProcessor() {
		return floatAndDoubleFieldProcessor;
	}


	public static JBBPParser getMessageParser() {
		return messageParser;
	}


	public static JBBPParser getPackagepParser() {
		return packageParser;
	}


	public static JBBPParser getRobotModeDataParser() {
		return robotModeDataParser;
	}


	public static JBBPParser getJointDataParser() {
		return jointDataParser;
	}


	public static JBBPParser getToolDataParser() {
		return toolDataParser;
	}


	public static JBBPParser getMasterboardDataParser() {
		return masterBoardDataParser;
	}


	public static JBBPParser getCartesianDataParser() {
		return cartesianDataParser;
	}


	public static JBBPParser getKinematicDataParser() {
		return kinematicDataParser;
	}


	public static JBBPParser getConfigurationDataParser() {
		return configurationDataParser;
	}


	public static JBBPParser getForceModeDataParser() {
		return forceModeDataParser;
	}


	public static JBBPParser getAdditionalDataParser() {
		return additionalDataParser;
	}


	public static JBBPParser getCalibrationDataParser() {
		return calibrationDataParser;
	}

	public static int getPackageLength(JBBPFieldStruct struct) {
		return struct.findFieldForNameAndType("packageLength", JBBPFieldInt.class).getAsInt();
	}
	
	public static RobotMessageType getRobotMessageType(JBBPFieldStruct struct) {
		return RobotMessageType.from(struct.findFieldForNameAndType("packageType", JBBPFieldUByte.class).getAsInt());
	}
	
	public static RobotPackageType getRobotPackageType(JBBPFieldStruct struct) {
		return RobotPackageType.from(struct.findFieldForNameAndType("packageType", JBBPFieldUByte.class).getAsInt());
	}

	public static JBBPParser getRealtimeMessageParser() {
		return realtimeMessageParser;
	}

	public static JBBPParser getRealtimePackageParser() {
		return realtimePackageParser;
	}
	

}
