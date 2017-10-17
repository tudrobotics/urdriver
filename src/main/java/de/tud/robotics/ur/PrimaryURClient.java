package de.tud.robotics.ur;

import java.io.IOException;
import java.io.InputStream;
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


public class PrimaryURClient extends CommonURClient {

	private static final int primaryPort = 30001;
	
	// data
	protected RobotModeData robotModeData;
	protected JointData jointData;
	protected ToolData toolData;
	protected MasterboardData masterBoardData;
	protected CartesianData cartesianData;
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
	
	public PrimaryURClient(String host) {		
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
		throw new RuntimeException("primary client is not implemented");
	}


	@Override
	protected void parse(InputStream in) throws IOException, ParsingException {
		throw new RuntimeException("primary client is not implemented");
	}

}
