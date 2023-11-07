package com.meta.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.CSenorDataDTO;
import com.meta.dto.CSensorDTO;
import com.meta.dto.DbSensor_InsertDTO;
import com.meta.dto.DefaultSensorDTO;
import com.meta.dto.GasSendDTO;
import com.meta.dto.GasSensorDTO;
import com.meta.dto.Mv_SensorDTO;
import com.meta.dto.Result_SensorDTO;
import com.meta.dto.SensorDTO;

public class CSensorDAOImpl implements CSensorDAO {
	
	@Autowired
	@Qualifier(value = "OneSqlSession")
	private SqlSession sqlsession;
	
	public static final String namespace = "mappers.csensorMapper";
	
	@Override
	public List<DefaultSensorDTO> AdminSensorSearch(DefaultSensorDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".AdminSensorSearch", dto);
	}
	
	@Override
	public List<DefaultSensorDTO> AdminSensorList() throws Exception {
		return sqlsession.selectList(namespace+".AdminSensorList");
	}
	
	@Override
	public List<Result_SensorDTO> Read_Sensor_Data(String sensor_name) throws Exception {
		return sqlsession.selectList(namespace+".Read_Sensor_Data", sensor_name);
	}
	
	@Override
	public List<CSensorDTO> Zone_Secetion_Sensor_List(CSensorDTO dto) throws Exception{
		return sqlsession.selectList(namespace+".Zone_Secetion_Sensor_List", dto);
	}
	
	@Override
	public void SensorDelete() throws Exception {
		sqlsession.delete(namespace+".SensorDelete");
	}
	
	@Override
	public List<CSenorDataDTO> notificationTest() throws Exception {
		return sqlsession.selectList(namespace+".notificationTest");
	}
	
	@Override
	public List<GasSensorDTO> Gas_AC01G04(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AC01G04", number_of_data);
	}
	
	@Override
	public List<GasSensorDTO> Gas_AC01G01(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AC01G01", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AC01G02(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AC01G02", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA10G03(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA10G03", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA10G04(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA10G04", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G03(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G03", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G05(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G05", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G06(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G06", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G07(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G07", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G01(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G01", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA12G02(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA12G02", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA14G05(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA14G05", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA14G06(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA14G06", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA14G07(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA14G07", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA14G08(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA14G08", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA14G09(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA14G09", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA15G01(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA15G01", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA15G02(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA15G02", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA16G09(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA16G09", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA16G10(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA16G10", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA16G11(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA16G11", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AA16G12(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AA16G12", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AB28G01(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AB28G01", number_of_data);
	}

	@Override
	public List<GasSensorDTO> Gas_AB28G02(Integer number_of_data) throws Exception {
		return sqlsession.selectList(namespace+".Gas_AB28G02", number_of_data);
	}

	//

	@Override
	public List<Result_SensorDTO> OneSensorList(Result_SensorDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".OneSensorList", dto);
	}
	
	public List<Result_SensorDTO> SensorGraphList(Result_SensorDTO dto) throws Exception {
		return sqlsession.selectList(namespace+".SensorGraphList", dto);
	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37C01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37C01");
	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA10C01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10C01");

	}

	@Override
	public List<Mv_SensorDTO> Read_AD37B05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37B05");
	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37B03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37B03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37B01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37B01");
	}

	@Override
	public List<Mv_SensorDTO> Read_AD37B02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37B02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37B04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37B04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G01");

	}

	@Override
	public List<Mv_SensorDTO> Read_AB28G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB28G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28G02");

	}

	@Override
	public List<Mv_SensorDTO> Read_AA10G03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10G03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA10G04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10G04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14G05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14G05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14G06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14G06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14G07() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14G07");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14G08() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14G08");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16G09() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16G09");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16G10() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16G10");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16G11() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16G11");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16G12() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16G12");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12G07() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12G07");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14G09() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14G09");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15G02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01G04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01G04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD35G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD35G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD35G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD35G02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD35G03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD35G03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD35G04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD35G04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD36G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD36G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD36G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD36G02");

	}

	@Override
	public List<Mv_SensorDTO> Read_AD36G03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD36G03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD36G04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD36G04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37G02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37G03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37G03");

	}
	
	@Override	
	public List<Mv_SensorDTO> Read_AD37G04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37G04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12L01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01L01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14L03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14L03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01L03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01L03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01L04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01L04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L12() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L12");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB24L05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB24L05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB23L13() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB23L13");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB24L06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB24L06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L07() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L07");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB24L07() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB24L07");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L08() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L08");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB24L08() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB24L08");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L09() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L09");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB24L09() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB24L09");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L10() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L10");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB31L10() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB31L10");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L11() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L11");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB31L11() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB31L11");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA16L12() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA16L12");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15L13() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15L13");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15L14() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15L14");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L14() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L14");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15L15() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15L15");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L15() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L15");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA15L16() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15L16");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB32L16() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB32L16");

	}	
	
	@Override
	public List<Mv_SensorDTO> Read_AA15L17() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA15L17");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB32L17() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB32L17");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA20L18() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA20L18");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB28L18() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28L18");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA14L19() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA14L19");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB28L19() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28L19");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12L20() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12L20");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB28L20() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28L20");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB28L21() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB28L21");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L22() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L22");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L23() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L23");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L24() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L24");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L25() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L25");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB22L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB22L01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB22L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB22L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB22L03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB22L03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB22L04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB22L04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB22L05() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB22L05");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27L21() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27L21");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC08L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC08L01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC08L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC08L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA10L01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10L01");

	}

	@Override
	public List<Mv_SensorDTO> Read_AA10L02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10L02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA10F06() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10F06");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12P01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12P01");

	}
	
	
	@Override
	public List<Mv_SensorDTO> Read_AB31P01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB31P01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01G01() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01G01");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA12P02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA12P02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AB27P02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AB27P02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AC01G02() throws Exception {
		return sqlsession.selectList(namespace+".Read_AC01G02");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AA10P03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AA10P03");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A04() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A04");

	}
	
	@Override
	public List<Mv_SensorDTO> Read_AD37A03() throws Exception {
		return sqlsession.selectList(namespace+".Read_AD37A03");

	}
	
	
	
	//
	
	public void RSensorDataInsert(DbSensor_InsertDTO dto) throws Exception {
		
		sqlsession.insert(namespace+".RSensorDataInsert", dto);
	}
	
	//Zone4
	public List<CSensorDTO> Zone4_List_1() throws Exception {
		return sqlsession.selectList(namespace+".Zone4_List_1");
	}
	
	public List<CSensorDTO> Zone4_List_2() throws Exception {
		return sqlsession.selectList(namespace+".Zone4_List_2");
	}
	
	public List<CSensorDTO> Zone4_List_3() throws Exception {
		return sqlsession.selectList(namespace+".Zone4_List_3");
	}
	
	//Zone3
	
	public List<CSensorDTO> Zone3_List_1() throws Exception {
		return sqlsession.selectList(namespace+".Zone3_List_1");
	}
	
	public List<CSensorDTO> Zone3_List_2() throws Exception {
		return sqlsession.selectList(namespace+".Zone3_List_2");
	}
	
	//Zone2
	
	public List<CSensorDTO> Zone2_List_1() throws Exception {
		return sqlsession.selectList(namespace+".Zone2_List_1");
	}
	
	public List<CSensorDTO> Zone2_List_2() throws Exception {
		return sqlsession.selectList(namespace+".Zone2_List_2");
	}
	
	public List<CSensorDTO> Zone2_List_3() throws Exception{
		return sqlsession.selectList(namespace+".Zone2_List_3");
	}
	
	public List<CSensorDTO> Zone2_List_4() throws Exception{
		return sqlsession.selectList(namespace+".Zone2_List_4");
	}
	
	public List<CSensorDTO> Zone2_List_5() throws Exception{
		return sqlsession.selectList(namespace+".Zone2_List_5");
	}
	
	public List<CSensorDTO> Zone2_List_6() throws Exception{
		return sqlsession.selectList(namespace+".Zone2_List_6");
	}
	
	public List<CSensorDTO> Zone2_List_7() throws Exception{
		return sqlsession.selectList(namespace+".Zone2_List_7");
	}
	
	//Zone1
	
	public List<CSensorDTO> Zone1_List_1() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_1");
	}
	
	public List<CSensorDTO> Zone1_List_2() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_2");
	}
	
	public List<CSensorDTO> Zone1_List_3() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_3");
	}
	
	public List<CSensorDTO> Zone1_List_4() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_4");
	}
	
	public List<CSensorDTO> Zone1_List_5() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_5");
	}
	
	public List<CSensorDTO> Zone1_List_6() throws Exception{
		return sqlsession.selectList(namespace+".Zone1_List_6");
	}

}
