// src/pages/Login.jsx
import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Search, Bell, Heart, User } from "lucide-react";
import { login } from "../api/auth";

export default function LoginPage() {
  const [formData, setFormData] = useState({
    loginId: "",
    password: "",
  });
  const [autoLogin, setAutoLogin] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleAutoLoginChange = (e) => {
    setAutoLogin(e.target.checked);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await login({
        loginId: formData.loginId,
        password: formData.password,
      });
      setMessage(response.data.message);
      setError("");

      // 자동 로그인 처리
      if (autoLogin) {
        localStorage.setItem("userId", response.data.userId);
        localStorage.setItem("loginId", formData.loginId);
      }

      // 로그인 성공 시 홈으로 리다이렉트 (가정: 홈 경로)
      navigate("/");
    } catch (err) {
      setError(err.message);
      setMessage("");
    }
  };

  const handleGoogleLogin = () => {
    // 구글 소셜 로그인 (가정: API 호출 또는 OAuth 리다이렉트)
    window.location.href = "http://localhost:8080/api/auth/google"; // 예시 URL
  };

  const handleFacebookLogin = () => {
    // 페이스북 소셜 로그인 (가정: API 호출 또는 OAuth 리다이렉트)
    window.location.href = "http://localhost:8080/api/auth/facebook"; // 예시 URL
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
        <div className="text-center font-medium">로그인</div>
        <div className="w-4"></div>
      </div>

      {/* 메인 콘텐츠 */}
      <div className="flex-1 p-6 flex flex-col">
        {/* 로고 및 부제 */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-emerald-500">RoadIt</h1>
          <p className="text-sm text-gray-600 mt-1">유학생을 위한 생활 팁스 서비스</p>
        </div>

        {/* 로그인 폼 */}
        <form onSubmit={handleLogin} className="space-y-4">
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

          {/* 자동 로그인 체크박스 */}
          <div className="flex items-center">
            <input
              type="checkbox"
              checked={autoLogin}
              onChange={handleAutoLoginChange}
              className="w-5 h-5"
            />
            <label className="ml-2 text-sm">자동 로그인</label>
          </div>

          {/* 로그인 버튼 */}
          <button
            type="submit"
            className="w-full p-3 bg-emerald-500 text-white rounded-md"
          >
            로그인
          </button>

          {/* 비밀번호 찾기 */}
          <div className="text-right text-sm text-gray-500">
            <Link to="/reset-password" className="underline">
              비밀번호를 잊으셨나요?
            </Link>
          </div>

          {/* 소셜 로그인 구분선 */}
          <div className="flex items-center my-4">
            <div className="flex-1 border-t border-gray-300"></div>
            <div className="px-3 text-xs text-gray-500">소셜 계정으로 간편 로그인</div>
            <div className="flex-1 border-t border-gray-300"></div>
          </div>

          {/* 소셜 로그인 버튼 */}
          <div className="flex justify-center gap-4">
            <button
              type="button"
              onClick={handleGoogleLogin}
              className="w-12 h-12 rounded-full border flex items-center justify-center"
            >
              <img src="/google-logo.svg" width={24} height={24} alt="Google" />
            </button>
            <button
              type="button"
              onClick={handleFacebookLogin}
              className="w-12 h-12 rounded-full border flex items-center justify-center bg-blue-600"
            >
              <img src="/facebook-logo.svg" width={24} height={24} alt="Facebook" />
            </button>
          </div>
        </form>

        {/* 계정 생성 */}
        <div className="mt-auto">
          <div className="text-center text-sm text-gray-500 mb-4">
            아직 계정이 없으신가요?
          </div>
          <Link to="/signup">
            <button className="w-full p-3 bg-emerald-500 text-white rounded-md">
              새 계정 만들기
            </button>
          </Link>
        </div>

        {/* 메시지 표시 */}
        {message && <p className="text-green-500 mt-4">{message}</p>}
        {error && <p className="text-red-500 mt-4">{error}</p>}
      </div>

      {/* 하단 네비게이션 */}
      <div className="flex justify-between items-center p-4 border-t">
        <Link to="/search" className="flex flex-col items-center">
          <Search className="h-6 w-6" />
          <span className="text-xs">검색</span>
        </Link>
        <Link to="/notifications" className="flex flex-col items-center">
          <Bell className="h-6 w-6" />
          <span className="text-xs">알림</span>
        </Link>
        <Link to="/" className="flex flex-col items-center">
          <div className="text-emerald-500 font-bold">RoadIt</div>
          <span className="text-xs">HOME</span>
        </Link>
        <Link to="/likes" className="flex flex-col items-center">
          <Heart className="h-6 w-6" />
          <span className="text-xs">LIKE</span>
        </Link>
        <Link to="/profile" className="flex flex-col items-center">
          <User className="h-6 w-6" />
          <span className="text-xs">MY</span>
        </Link>
      </div>
    </div>
  );
}