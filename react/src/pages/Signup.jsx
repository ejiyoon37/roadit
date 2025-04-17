import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signup, sendVerificationCode, verifyCode } from "../api/auth";
import { SITE_NAME } from "../constants";

export default function Signup() {
  const [formData, setFormData] = useState({
    email: "",
    loginId: "",
    password: "",
    nickname: "",
    name: "",
    nation: "1",
    job: "1",
    school: "1",
    residencePeriod: 12,
    willSettle: true,
    provider: "ROADIT",
  });
  const [code, setCode] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [isCodeSent, setIsCodeSent] = useState(false);
  const [isVerified, setIsVerified] = useState(false);
  const navigate = useNavigate();

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
    if (formData.password !== confirmPassword) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }
    if (!isVerified) {
      setError("이메일 인증을 완료해주세요.");
      return;
    }
    try {
      const response = await signup(formData);
      setMessage(response.data.message);
      setError("");
      navigate("/"); // 성공 시 로그인 페이지로 이동
    } catch (err) {
      setError(err.message || "회원가입에 실패했습니다.");
      setMessage("");
    }
  };

  return (
    <div className="max-w-md mx-auto h-screen flex flex-col bg-white">
      {/* 상태 바 */}
      <div className="flex justify-between items-center p-2 text-xs">
        <div>12:30</div>
        <div className="flex items-center gap-1">
          <div className="w-3 h-3">▼</div>
          <div className="w-3 h-3">▌</div>
          <div className="w-3 h-3">▌</div>
        </div>
      </div>

      {/* 헤더 */}
      <div className="flex items-center justify-between p-4 border-b">
        <button className="text-xl">×</button>
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
            <button
              type="button"
              onClick={handleSendCode}
              className="p-3 bg-emerald-500 text-white rounded-md"
              disabled={isCodeSent || !formData.email}
            >
              인증번호 전송
            </button>
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
            placeholder="로그인 ID"
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
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
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
          <input
            type="text"
            name="nation"
            value={formData.nation}
            onChange={handleInputChange}
            placeholder="국가"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="text"
            name="job"
            value={formData.job}
            onChange={handleInputChange}
            placeholder="직업"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="text"
            name="school"
            value={formData.school}
            onChange={handleInputChange}
            placeholder="학교"
            className="w-full p-3 border rounded-md"
            required
          />
          <input
            type="number"
            name="residencePeriod"
            value={formData.residencePeriod}
            onChange={handleInputChange}
            placeholder="거주 기간 (개월)"
            className="w-full p-3 border rounded-md"
            required
          />
          <div className="flex items-center">
            <input
              type="checkbox"
              name="willSettle"
              checked={formData.willSettle}
              onChange={handleInputChange}
              className="w-5 h-5"
            />
            <label className="ml-2 text-sm">정착 의사 있음</label>
          </div>

          <button
            type="submit"
            className="w-full p-3 bg-emerald-500 text-white rounded-md"
            disabled={!isVerified}
          >
            회원가입
          </button>
        </form>

        {/* 메시지 표시 */}
        {message && <p className="text-green-500 mt-4 text-center">{message}</p>}
        {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
      </div>
    </div>
  );
}