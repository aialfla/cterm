package com.ezen.cterm.vo;

public class MemberVo {
	private int    id;         // 사원번호
	private String name;       // 이름
	private String pw;         // 비밀번호
	private int    dept;       // 부서 ( 1:기획부 / 2:디자인부 / 3:개발부 )
	private int    duty;       // 직급 ( 0:관리자 / 1:대표 / 2:부장 / 3:팀장 / 4:사원 )
	private String joindate;   // 입사일
	private String retiredate; // 퇴사일
	private int    state;      // 상태 ( 0:퇴사 / 1:재직 / 2:휴직 )
	private String tel;        // 연락처
	private String mail;       // 이메일
	private String addr;       // 주소
	private int    vaca;       // 총 연차
	private String npw;        // 새 비밀번호
	

	public String getNpw() {
		return npw;
	}
	public void setNpw(String npw) {
		this.npw = npw;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getDept() {
		return dept;
	}
	public String getDeptName() {
		String result="";
		switch (dept) {
		case 1 : result = "기획부"; break;
		case 2 : result = "디자인부"; break;
		case 3 : result = "개발부"; break;
		default : result = "관리부";
		}
		return result;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	public int getDuty() {
		return duty;
	}
	public String getDutyName() {
		String result="";
		switch (duty) {
		case 0 : result = "관리자"; break;
		case 1 : result = "대표"; break;
		case 2 : result = "부장"; break;
		case 3 : result = "팀장"; break;
		default : result = "사원";
		}
		return result;
	}
	public void setDuty(int duty) {
		this.duty = duty;
	}
	public String getjoindate() {
		return joindate;
	}
	public void setjoindate(String joindate) {
		this.joindate = joindate;
	}
	public String getretiredate() {
		return retiredate;
	}
	public void setretiredate(String retiredate) {
		this.retiredate = retiredate;
	}
	public int getState() {
		return state;
	}
	public String getStateName() {
		String result="";
		switch (state) {
		case 0 : result = "퇴사"; break;
		case 1 : result = "재직"; break;
		case 2 : result = "휴직"; break;
		}
		return result;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getVaca() {
		return vaca;
	}
	public void setVaca(int vaca) {
		this.vaca = vaca;
	}
	@Override
	public String toString() {
		return "MemberVo [id=" + id + ", name=" + name + ", pw=" + pw + ", dept=" + dept + ", duty=" + duty
				+ ", joindate=" + joindate + ", retiredate=" + retiredate + ", state=" + state + ", tel=" + tel
				+ ", mail=" + mail + ", addr=" + addr + ", vaca=" + vaca + "]";
	}
	
	
}
