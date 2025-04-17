import React, { useState } from "react";
import { resetPassword } from "../api/auth";
import { SITE_NAME } from "../constants";

export default function ForgotPassword() {
  const [formData, setFormData] = useState({
    loginId: "",
    email: "",
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(formData.email)) {
      setError("유효한 이메일 주소를 입력해주세요.");
      return;
    }
    try {
      const response = await resetPassword(formData);
      setMessage(response.data.message);
      setError("");
    } catch (err) {
      setError(err.message || "비밀번호 찾기에 실패했습니다.");
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
        <div className="text-center font-medium">비밀번호 찾기</div>
        <div className="w-4"></div>
      </div>

      {/* 메인 콘텐츠 */}
      <div className="flex-1 p-6 flex flex-col">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-emerald-500">{SITE_NAME}</h1>
          <p className="text-sm text-gray-600 mt-1">유학생을 위한 생활 팁스 서비스</p>
        </div>

        {/* 비밀번호 찾기 폼 */}
        <form onSubmit={handleSubmit} className="space-y-4">
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
            type="email"
            name="email"
            value={formData.email}
            onChange={handleInputChange}
            placeholder="이메일"
            className="w-full p-3 border rounded-md"
            required
          />
          <button
            type="submit"
            className="w-full p-3 bg-emerald-500 text-white rounded-md"
          >
            임시 비밀번호 요청
          </button>
        </form>

        {/* 메시지 표시 */}
        {message && <p className="text-green-500 mt-4 text-center">{message}</p>}
        {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
      </div>
    </div>
  );
}