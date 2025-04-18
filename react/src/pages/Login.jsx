import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { signup, sendVerificationCode, verifyCode } from "../api/auth";
import { SITE_NAME } from "../constants";

export default function Signup() {
  const [formData, setFormData] = useState({
    email: "",
    loginId: "",
    password: "",
    confirmPassword: "",
    nickname: "",
    name: "",
    nation: "1",
    job: "1",
    school: "1",
    residencePeriod: "1년",
    willSettle: true,
    provider: "ROADIT",
  });
  const [code, setCode] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [isCodeSent, setIsCodeSent] = useState(false);
  const [isVerified, setIsVerified] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [timer, setTimer] = useState(180); // 3분 타이머 (180초)
  const navigate = useNavigate();

  // 타이머 로직
  useEffect(() => {
    let interval;
    if (isCodeSent && !isVerified && timer > 0) {
      interval = setInterval(() => {
        setTimer((prev) => prev - 1);
      }, 1000);
    }
    return () => clearInterval(interval);
  }, [isCodeSent, isVerified, timer]);

  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${minutes.toString().padStart(2, "0")}:${secs.toString().padStart(2, "0")}`;
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSendCode = async () => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      setError("유효한 이메일 주소를 입력해주세요.");
      return;
    }
    try {
      const response = await sendVerificationCode(formData.email);
      setMessage(response.data.message);
      setIsCodeSent(true);
      setTimer(180); // 타이머 초기화
      setError("");
    } catch (err) {
      setError(err.message || "인증번호 전송에 실패했습니다.");
      setMessage("");
    }
  };

  const handleVerifyCode = async () => {
    try {
      const response = await verifyCode(formData.email, code);
      setMessage(response.data.message);
      setIsVerified(true);
      setError("");
    } catch (err) {
      setError(err.message || "인증번호가 일치하지 않습니다.");
      setMessage("");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password.length < 8) {
      setError("비밀번호는 최소 8자 이상이어야 합니다.");
      return;
    }
    if (formData.password !== formData.confirmPassword) {
      setShowModal(true);
      return;
    }
    if (!isVerified) {
      setError("이메일 인증을 완료해주세요.");
      return;
    }
    try {
      const response = await signup({
        email: formData.email,
        loginId: formData.loginId,
        password: formData.password,
        nickname: formData.nickname,
        name: formData.name,
        nation: formData.nation,
        job: formData.job,
        school: formData.school,
        residencePeriod: parseInt(formData.residencePeriod.replace("년", "")) * 12, // 년 단위를 개월로 변환
        willSettle: formData.willSettle,
        provider: formData.provider,
      });
      setMessage(response.data.message);
      setError("");
      navigate("/signup-success"); // 성공 시 완료 페이지로 이동
    } catch (err) {
      setError(err.message || "회원가입에 실패했습니다.");
      setMessage("");
    }
  };

  return (
    <div className="max-w-md mx-auto h-screen flex flex-col bg-white">
      {/* 헤더 */}
      <div className="flex items-center justify-between p-4 border-b">
        <button className="text-xl" onClick={() => navigate(-1)}>
          ×
        </button>
        <div className="text-center font-medium">회원가입</div>
        <div className="w-4"></div>
      </div>

      {/* 메인 콘텐츠 */}
      <div className="flex-1 p-6 flex flex-col">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-emerald-500">{SITE_NAME}</h1>
          <p className="text-sm text-gray-600 mt-1">유학생을 위한 생활 팁스 서비스</p>
        </div>

        {/* 회원가입 폼 */}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="flex items-center space-x-2">
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleInputChange}
              placeholder="이메일"
              className="w-full p-3 border rounded-md"
              required
              disabled={isVerified}
            />
            {!isVerified && (
              <button
                type="button"
                onClick={handleSendCode}
                className="p-3 bg-emerald-500 text-white rounded-md"
                disabled={isCodeSent || !formData.email}
              >
                인증번호 전송
              </button>
            )}
          </div>

          {isCodeSent && !isVerified && (
            <div className="flex items-center space-x-2">
              <input
                type="text"
                value={code}
                onChange={(e) => setCode(e.target.value)}
                placeholder="인증번호 입력"
                className="w-full p-3 border rounded-md"
                required
              />
              <div className="text-sm text-gray-500">{formatTime(timer)}</div>
              <button
                type="button"
                onClick={handleVerifyCode}
                className="p-3 bg-emerald-500 text-white rounded-md"
                disabled={!code}
              >
                인증
              </button>
            </div>
          )}

          <input
            type="text"
            name="loginId"
            value={formData.loginId}
            onChange={handleInputChange}
            placeholder="아이디"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            placeholder="비밀번호"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleInputChange}
            placeholder="비밀번호 확인"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="text"
            name="nickname"
            value={formData.nickname}
            onChange={handleInputChange}
            placeholder="닉네임"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            placeholder="이름"
            className="w-full p-3 border rounded-md"
            required
          />
          <select
            name="nation"
            value={formData.nation}
            onChange={handleInputChange}
            className="w-full p-3 border rounded-md"
            required
          >
            <option value="1">국가 1</option>
            <option value="2">국가 2</option>
            <option value="3">국가 3</option>
          </select>
          <select
            name="job"
            value={formData.job}
            onChange={handleInputChange}
            className="w-full p-3 border rounded-md"
            required
          >
            <option value="1">직업 1</option>
            <option value="2">직업 2</option>
            <option value="3">직업 3</option>
          </select>
          <select
            name="school"
            value={formData.school}
            onChange={handleInputChange}
            className="w-full p-3 border rounded-md"
            required
          >
            <option value="1">학교 1</option>
            <option value="2">학교 2</option>
            <option value="3">학교 3</option>
          </select>
          <select
            name="residencePeriod"
            value={formData.residencePeriod}
            onChange={handleInputChange}
            className="w-full p-3 border rounded-md"
            required
          >
            <option value="1년">1년</option>
            <option value="1-3개월">1-3개월</option>
            <option value="1-2년">1-2년</option>
            <option value="3년 이상">3년 이상</option>
            <option value="3-6개월">3-6개월</option>
          </select>
          <div className="flex flex-col space-y-2">
            <label className="text-sm">유학생생활 기간</label>
            <div className="flex flex-wrap gap-2">
              <label className="flex items-center">
                <input
                  type="radio"
                  name="residencePeriod"
                  value="1-3개월"
                  checked={formData.residencePeriod === "1-3개월"}
                  onChange={handleInputChange}
                  className="mr-2"
                />
                1-3개월
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="residencePeriod"
                  value="1-2년"
                  checked={formData.residencePeriod === "1-2년"}
                  onChange={handleInputChange}
                  className="mr-2"
                />
                1-2년
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="residencePeriod"
                  value="3-6개월"
                  checked={formData.residencePeriod === "3-6개월"}
                  onChange={handleInputChange}
                  className="mr-2"
                />
                3-6개월
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="residencePeriod"
                  value="3년 이상"
                  checked={formData.residencePeriod === "3년 이상"}
                  onChange={handleInputChange}
                  className="mr-2"
                />
                3년 이상
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="residencePeriod"
                  value="1년"
                  checked={formData.residencePeriod === "1년"}
                  onChange={handleInputChange}
                  className="mr-2"
                />
                약 1년
              </label>
            </div>
          </div>
          <div className="flex flex-col space-y-2">
            <label className="text-sm">막간 정착의</label>
            <div className="flex gap-2">
              <label className="flex items-center">
                <input
                  type="radio"
                  name="willSettle"
                  value={true}
                  checked={formData.willSettle === true}
                  onChange={() => setFormData((prev) => ({ ...prev, willSettle: true }))}
                  className="mr-2"
                />
                만 14세 이상입니다. (필수)
              </label>
              <label className="flex items-center">
                <input
                  type="radio"
                  name="willSettle"
                  value={false}
                  checked={formData.willSettle === false}
                  onChange={() => setFormData((prev) => ({ ...prev, willSettle: false }))}
                  className="mr-2"
                />
                이용약관 (필수)
              </label>
            </div>
          </div>

          <button
            type="submit"
            className={`w-full p-3 rounded-md text-white ${
              isVerified ? "bg-emerald-500" : "bg-gray-300 cursor-not-allowed"
            }`}
            disabled={!isVerified}
          >
            회원가입 완료하기
          </button>
        </form>

        {/* 메시지 표시 */}
        {message && <p className="text-green-500 mt-4 text-center">{message}</p>}
        {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
        {!isVerified && (
          <p className="text-red-500 mt-4 text-center">이메일 인증을 완료해주세요.</p>
        )}
      </div>

      {/* 비밀번호 불일치 모달 */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <div className="bg-white p-6 rounded-md shadow-lg">
            <h2 className="text-lg font-medium mb-4">비밀번호 불일치</h2>
            <p className="text-sm text-gray-600 mb-4">비밀번호가 일치하지 않습니다. 다시 확인해주세요.</p>
            <button
              onClick={() => setShowModal(false)}
              className="w-full p-2 bg-emerald-500 text-white rounded-md"
            >
              확인
            </button>
          </div>
        </div>
      )}
    </div>
  );
}