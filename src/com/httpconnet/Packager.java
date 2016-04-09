package com.httpconnet;

public class Packager {

	
	//------------------------------------------------------------------
	public String registerPackager(String tel) {
		StringBuffer mes = new StringBuffer();
		mes.append("whetherRegister"+ "#");
		mes.append(tel + "#");
		
		return mes.toString();
	}
	public String registerPackager1(String tel,String vfc) {
		StringBuffer mes = new StringBuffer();
		mes.append("checkRegisterCode"+ "#");
		mes.append(tel + "#");
		mes.append(vfc + "#");
		return mes.toString();
	}
	public String registerPackager2(String tel,String pw) {
		StringBuffer mes = new StringBuffer();
		mes.append("insertUserInfo"+ "#");
		mes.append(tel + "#");
		mes.append(pw + "#");
		return mes.toString();
	}
	public String loginPackager(String tel,String pw) {
		StringBuffer mes = new StringBuffer();
		mes.append("login"+ "#");
		mes.append(tel + "#");
		mes.append(pw + "#");
		return mes.toString();
	}
//---------------------------个人信息-------------------------------
	public String getUserInfoPackager(String id) {
		StringBuffer mes = new StringBuffer();
		mes.append("selectUserInfo"+ "#");
		mes.append(id+ "#");

		return mes.toString();
	}
//--------------------修改昵称-------------------------
	public String updateUserNamePackager(String id,String name) {
		StringBuffer mes = new StringBuffer();
		mes.append("updateUserName"+ "#");
		mes.append(id+ "#");
		mes.append(name+ "#");

		return mes.toString();
	}
	//--------------------修改性别---------------------------
		public String updateUserSexPackager(String id,String sex) {
			StringBuffer mes = new StringBuffer();
			mes.append("updateUserSex"+ "#");
			mes.append(id+ "#");
			mes.append(sex+ "#");

			return mes.toString();
		}
		//---------------------------修改密码------------------
		public String updateUserPwPackager(String id,String old,String newpwd) {
			StringBuffer mes = new StringBuffer();
			mes.append("updateUserPw"+ "#");
			mes.append(id+ "#");
			mes.append(old+ "#");
			mes.append(newpwd+ "#");

			return mes.toString();
		}
		//------------------------获取账户金额--------------------------
		public String selectUserCashPackager(String tel) {
			StringBuffer mes = new StringBuffer();
			mes.append("selectUserCash"+ "#");
			mes.append(tel+ "#");
		
			return mes.toString();
		}
		//---------------------获取信用额--------------------
		public String selectUserCreditPackager(String id) {
			StringBuffer mes = new StringBuffer();
			mes.append("selectUserCredit"+ "#");
			mes.append(id+ "#");
		
			return mes.toString();
		}
		//---------------------添加充电站------------------
		public String addStationPackager(String user_id, String station_name,String address1, String address2, String longitude,String latitude, String image) {
			StringBuffer mes = new StringBuffer();
//			mes.append("addStation"+ "\n");
//			mes.append(user_id+ "\n");
//			mes.append(station_name+"\n");
//			mes.append(address1+"\n");
//			mes.append(address2+"\n");
//			mes.append(longitude+"\n");
//			mes.append(latitude+"\n");
//			mes.append(image+"\n");
			mes.append("addStation"+ "#");
			mes.append(user_id+ "#");
			mes.append(station_name+"#");
			mes.append(address1+"#");
			mes.append(address2+"#");
			mes.append(longitude+"#");
			mes.append(latitude+"#");
			mes.append(image+"#");
		
			return mes.toString();
		}
		//---------------------添加充电桩--------------------
		public String addSpace(String mechine_id,String station_id,String user_id,String space_address,String price_one,String price_two,String space_image1){
			StringBuffer mes = new StringBuffer();
			mes.append("addSpace"+ "#");
			mes.append(mechine_id+ "#");
			mes.append(station_id+"#");
			mes.append( user_id+"#");
			mes.append(space_address+"#");
			mes.append(price_one+"#");
			mes.append(price_two+"#");		
			mes.append(space_image1+"#");
		
			return mes.toString();
		}
		
		public String addTimeSlicePackager(String positionid,String beginTime,String endTime){
			StringBuffer mes = new StringBuffer();
			mes.append("addTimeSlice"+ "#");
			mes.append(positionid+ "#");
			mes.append(beginTime+"#");	
			mes.append(endTime+"#");
		
			return mes.toString();
		}
	
	//*******************************************查看充电站信息**************
	public String LookStationPackager(String chargeid) {
		StringBuffer mes = new StringBuffer();
		mes.append("selectStationThroughId"+ "#");
		mes.append(chargeid+ "#");
	
		return mes.toString();
	}
	//****************************************查看全部充电位*********
	public String LookPositionPackager(String chargeid) {
		StringBuffer mes = new StringBuffer();
		mes.append("selectSpaceThroughStation"+ "#");
		mes.append(chargeid+ "#");
	
		return mes.toString();
	}
	//**************************************查看充电位具体信息
	public String selectSpaceInfoPackager(String space_id) {
		StringBuffer mes = new StringBuffer();
		mes.append("selectSpaceInfo"+ "#");
		mes.append(space_id+ "#");
	
		return mes.toString();
	}
	//-------------------------------------退出----------------------
		public String quitLoginPackager(String tel){
			StringBuffer mes = new StringBuffer();
			mes.append("quitLogin"+ "#");
			mes.append(tel+ "#");	
			return mes.toString();
		}
		//--------------------------下单------------------------
//		public String createOrderPackager(String user_id,String space_id,String start_time,String end_time,String use_power,String elec,String total_price){
//		    StringBuffer mes = new StringBuffer();
//			mes.append("createOrder"+ "#");
//			mes.append(user_id+ "#");
//			mes.append(space_id+"#");	
//			mes.append(start_time+"#");
//			mes.append(end_time+ "#");
//			mes.append(use_power+"#");	
//			mes.append(elec+"#");
//			mes.append(total_price+"#");
//		
//			return mes.toString();
//		}
		public String createOrderPackager(String user_id,String space_id,
				String start_time,String end_time,String use_power,String elec,
				String power_price,String type,String total_price){
		    StringBuffer mes = new StringBuffer();
			mes.append("createOrder"+ "#");
			mes.append(user_id+ "#");
			mes.append(space_id+"#");	
			mes.append(start_time+"#");
			mes.append(end_time+ "#");
			mes.append(use_power+"#");	
			mes.append(elec+"#");
			mes.append(power_price+"#");
			mes.append(type+"#");
			mes.append(total_price+"#");
			return mes.toString();
		}
		//--------------------------支付------------------------
				public String payOrderPackager(String order_id,String user_tel,String pay_pw,String total_price)
				{
					StringBuffer mes = new StringBuffer();
					mes.append("payOrder"+ "#");
					mes.append(order_id+ "#");
					mes.append(user_tel+"#");	
					mes.append(pay_pw+"#");
					mes.append(total_price+ "#");
				
					return mes.toString();
				}
				//--------------------------查询订单------------------------
				public String selectOrderPackager(String user_id)
				{
					StringBuffer mes = new StringBuffer();
					mes.append("selectOrder"+ "#");
					mes.append(user_id+ "#");				
					return mes.toString();
				}
				//------------------------------------查看订单详情------------------
				public String selectOrderInfoPackager(String order_id)
				{
					StringBuffer mes = new StringBuffer();
					mes.append("selectOrderInfo"+ "#");
					mes.append(order_id+ "#");				
					return mes.toString();
				}
				//------------------------------------取消订单------------------
				public String cancelOrderPackager(String order_id)
				{
					StringBuffer mes = new StringBuffer();
					mes.append("cancelOrder"+ "#");
					mes.append(order_id+ "#");				
					return mes.toString();
				}
		
}
