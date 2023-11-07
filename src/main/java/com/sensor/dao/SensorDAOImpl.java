package com.sensor.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.meta.dto.Mv_SensorDTO;
import com.meta.dto.RSensorDTO;
import com.meta.dto.SensorDTO;

public class SensorDAOImpl implements SensorDAO {

	@Autowired
	@Qualifier("db2SqlSessionTemplate")
	private SqlSession sqlSessionSub;
	
	public static final String namespace = "mappers.sensorMapper";
	
	
	//
	
	@Override
	public List<SensorDTO> SensorStatTest() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".SensorStatTest");
	}
	
	@Override
	public List<SensorDTO> ValueSensorList(SensorDTO dto) throws Exception{
		
		return sqlSessionSub.selectList(namespace+".ValueSensorList", dto);
		
	}
	
	@Override
	public List<RSensorDTO> AA10C01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10C01");
	}
	
	@Override
	public List<RSensorDTO> AA10F01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10F01");
	}
	
	@Override
	public List<RSensorDTO> AA10G03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10G03");
	}
	
	@Override
	public List<RSensorDTO> AA10G04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10G04");
	}

	@Override
	public List<RSensorDTO> AA10L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10L01");
	}

	@Override
	public List<RSensorDTO> AA10L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10L02");
	}
	
	@Override
	public List<RSensorDTO> AA10P03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA10P03");
	}

	@Override
	public List<RSensorDTO> AA12F01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12F01");
	}

	@Override
	public List<RSensorDTO> AA12G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G01");
	}

	@Override
	public List<RSensorDTO> AA12G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G02");
	}

	@Override
	public List<RSensorDTO> AA12G03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G03");
	}

	@Override
	public List<RSensorDTO> AA12G05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G05");
	}

	@Override
	public List<RSensorDTO> AA12G06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G06");
	}

	@Override
	public List<RSensorDTO> AA12G07() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12G07");
	}
	
	@Override
	public List<RSensorDTO> AA12L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12L01");
	}
	
	@Override
	public List<RSensorDTO> AA12L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12L02");
	}
	
	@Override
	public List<RSensorDTO> AA12L20() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12L20");
	}
	
	@Override
	public List<RSensorDTO> AA12P01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12P01");
	}

	@Override
	public List<RSensorDTO> AA12P02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA12P02");
	}

	@Override
	public List<RSensorDTO> AA14F01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14F01");
	}
	
	@Override
	public List<RSensorDTO> AA14G05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14G05");
	}

	@Override
	public List<RSensorDTO> AA14G06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14G06");
	}

	@Override
	public List<RSensorDTO> AA14G07() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14G07");
	}

	@Override
	public List<RSensorDTO> AA14G08() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14G08");
	}

	@Override
	public List<RSensorDTO> AA14G09() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14G09");
	}

	@Override
	public List<RSensorDTO> AA14L03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14L03");
	}

	@Override
	public List<RSensorDTO> AA14L19() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA14L19");
	}

	@Override
	public List<RSensorDTO> AA15G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15G01");
	}
	
	@Override
	public List<RSensorDTO> AA15G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15G02");
	}
	
	@Override
	public List<RSensorDTO> AA15L13() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15L13");
	}
	
	@Override
	public List<RSensorDTO> AA15L14() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15L14");
	}
	
	@Override
	public List<RSensorDTO> AA15L15() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15L15");
	}

	@Override
	public List<RSensorDTO> AA15L16() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15L16");
	}

	@Override
	public List<RSensorDTO> AA15L17() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA15L17");
	}
	
	@Override
	public List<RSensorDTO> AA16G09() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16G09");
	}

	@Override
	public List<RSensorDTO> AA16G10() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16G10");
	}

	@Override
	public List<RSensorDTO> AA16G11() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16G11");
	}

	@Override
	public List<RSensorDTO> AA16G12() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16G12");
	}

	@Override
	public List<RSensorDTO> AA16L04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L04");
	}

	@Override
	public List<RSensorDTO> AA16L05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L05");
	}

	@Override
	public List<RSensorDTO> AA16L06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L06");
	}

	@Override
	public List<RSensorDTO> AA16L07() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L07");
	}
	
	@Override
	public List<RSensorDTO> AA16L08() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L08");
	}
	
	@Override
	public List<RSensorDTO> AA16L09() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L09");
	}
	
	@Override
	public List<RSensorDTO> AA16L10() throws Exception{
		
		return sqlSessionSub.selectList(namespace+".AA16L10");
	}
	
	@Override
	public List<RSensorDTO> AA16L11() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L11");
	}

	@Override
	public List<RSensorDTO> AA16L12() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA16L12");
	}

	@Override
	public List<RSensorDTO> AA20L18() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AA20L18");
	}
	
	@Override
	public List<RSensorDTO> AB22L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB22L01");
	}

	@Override
	public List<RSensorDTO> AB22L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB22L02");
	}

	@Override
	public List<RSensorDTO> AB22L03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB22L03");
	}

	@Override
	public List<RSensorDTO> AB22L04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB22L04");
	}

	@Override
	public List<RSensorDTO> AB22L05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB22L05");
	}

	@Override
	public List<RSensorDTO> AB23L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L01");
	}

	@Override
	public List<RSensorDTO> AB23L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L02");
	}

	@Override
	public List<RSensorDTO> AB23L03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L03");
	}
	
	@Override
	public List<RSensorDTO> AB23L04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L04");
	}
	
	@Override
	public List<RSensorDTO> AB23L12() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L12");
	}
	
	@Override
	public List<RSensorDTO> AB23L13() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB23L13");
	}
	
	@Override
	public List<RSensorDTO> AB24L05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB24L05");
	}

	@Override
	public List<RSensorDTO> AB24L06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB24L06");
	}

	@Override
	public List<RSensorDTO> AB24L07() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB24L07");
	}
	
	@Override
	public List<RSensorDTO> AB24L08() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB24L08");
	}

	@Override
	public List<RSensorDTO> AB24L09() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB24L09");
	}
	
	@Override
	public List<RSensorDTO> AB27F01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27F01");
	}

	@Override
	public List<RSensorDTO> AB27L14() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L14");
	}

	@Override
	public List<RSensorDTO> AB27L15() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L15");
	}

	@Override
	public List<RSensorDTO> AB27L21() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L21");
	}

	@Override
	public List<RSensorDTO> AB27L22() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L22");
	}

	@Override
	public List<RSensorDTO> AB27L23() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L23");
	}
	
	@Override
	public List<RSensorDTO> AB27L24() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L24");
	}
	
	@Override
	public List<RSensorDTO> AB27L25() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27L25");
	}
	
	@Override
	public List<RSensorDTO> AB27P02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB27P02");
	}
	
	@Override
	public List<RSensorDTO> AB28G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28G01");
	}
	
	@Override
	public List<RSensorDTO> AB28G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28G02");
	}

	@Override
	public List<RSensorDTO> AB28L18() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28L18");
	}
	
	@Override
	public List<RSensorDTO> AB28L19() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28L19");
	}

	@Override
	public List<RSensorDTO> AB28L20() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28L20");
	}

	@Override
	public List<RSensorDTO> AB28L21() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB28L21");
	}

	@Override
	public List<RSensorDTO> AB31L10() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB31L10");
	}

	@Override
	public List<RSensorDTO> AB31L11() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB31L11");
	}

	@Override
	public List<RSensorDTO> AB31P01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB31P01");
	}

	@Override
	public List<RSensorDTO> AB32L16() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB32L16");
	}
	
	@Override
	public List<RSensorDTO> AB32L17() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AB32L17");
	}
		
	@Override
	public List<RSensorDTO> AC01G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01G01");
	}
	
	@Override
	public List<RSensorDTO> AC01G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01G02");
	}
	
	@Override
	public List<RSensorDTO> AC01G04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01G04");
	}
	
	@Override
	public List<RSensorDTO> AC01L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L01");
	}

	@Override
	public List<RSensorDTO> AC01L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L02");
	}

	@Override
	public List<RSensorDTO> AC01L03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L03");
	}
	
	@Override
	public List<RSensorDTO> AC01L04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L04");
	}

	@Override
	public List<RSensorDTO> AC01L05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L05");
	}

	@Override
	public List<RSensorDTO> AC01L06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L06");
	}

	@Override
	public List<RSensorDTO> AC01L07() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L07");
	}

	@Override
	public List<RSensorDTO> AC01L08() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L08");
	}

	@Override
	public List<RSensorDTO> AC01L09() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC01L09");
	}

	@Override
	public List<RSensorDTO> AC08L01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+"AC08L01");
	}

	@Override
	public List<RSensorDTO> AC08L02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AC08L02");
	}
	
	@Override
	public List<RSensorDTO> AD35G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD35G01");
	}
	
	@Override
	public List<RSensorDTO> AD35G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD35G02");
	}
	
	@Override
	public List<RSensorDTO> AD35G03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD35G03");
	}
	
	@Override
	public List<RSensorDTO> AD35G04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD35G04");
	}

	@Override
	public List<RSensorDTO> AD36G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD36G01");
	}

	@Override
	public List<RSensorDTO> AD36G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD36G02");
	}
	
	@Override
	public List<RSensorDTO> AD36G03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD36G03");
	}

	@Override
	public List<RSensorDTO> AD36G04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD36G04");
	}

	@Override
	public List<RSensorDTO> AD37A01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A01");
	}

	@Override
	public List<RSensorDTO> AD37A02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A02");
	}

	@Override
	public List<RSensorDTO> AD37A03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A03");
	}

	@Override
	public List<RSensorDTO> AD37A04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A04");
	}

	@Override
	public List<RSensorDTO> AD37A05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A05");
	}
	
	@Override
	public List<RSensorDTO> AD37A06() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37A06");
	}
	
	@Override
	public List<RSensorDTO> AD37B01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37B01");
	}

	@Override
	public List<RSensorDTO> AD37B02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37B02");
	}

	@Override
	public List<RSensorDTO> AD37B03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37B03");
	}

	@Override
	public List<RSensorDTO> AD37B04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37B04");
	}

	@Override
	public List<RSensorDTO> AD37B05() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37B05");
	}

	@Override
	public List<RSensorDTO> AD37C01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37C01");
	}

	@Override
	public List<RSensorDTO> AD37G01() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37G01");
	}

	@Override
	public List<RSensorDTO> AD37G02() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37G02");
	}
	
	@Override
	public List<RSensorDTO> AD37G03() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37G03");
	}
	
	@Override
	public List<RSensorDTO> AD37G04() throws Exception {
		
		return sqlSessionSub.selectList(namespace+".AD37G04");
	}
	
	
	
}
