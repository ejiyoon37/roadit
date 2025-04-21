import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Search, Bell, Heart, User } from "lucide-react";
import { SITE_NAME } from "../constants";

export default function LoginPage() {
  const [formData, setFormData] = useState({
    loginId: "",
    password: "",
  });
  const [autoLogin, setAutoLogin] = useState(false);
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // API 기본 URL - Vite 환경 변수 사용
  const API_BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

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
      const response = await fetch(`${API_BASE_URL}/api/login/roadit`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          loginId: formData.loginId,
          password: formData.password,
        }),
      });

      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.error?.message || "로그인에 실패했습니다.");
      }

      setMessage(data.message);
      setError("");

      if (autoLogin) {
        localStorage.setItem("userId", data.userId);
        localStorage.setItem("loginId", formData.loginId);
      }

      navigate("/intro");
    } catch (err) {
      const errorMessage = err.message || "로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.";
      setError(errorMessage);
      setMessage("");
    }
  };

  const handleGoogleLogin = async () => {
    try {
      window.location.href = `${API_BASE_URL}/api/auth/google`;
    } catch (err) {
      setError("구글 로그인에 실패했습니다. 서버 연결을 확인해주세요.");
      setMessage("");
    }
  };

  const handleFacebookLogin = async () => {
    try {
      window.location.href = `${API_BASE_URL}/api/auth/facebook`;
    } catch (err) {
      setError("페이스북 로그인에 실패했습니다. 서버 연결을 확인해주세요.");
      setMessage("");
    }
  };

  return (
    <div className="max-w-md mx-auto h-screen flex flex-col bg-white">
      {/* 헤더 */}
      <div className="flex items-center justify-between p-4 border-b">
        <button className="text-xl" onClick={() => navigate("/intro")}>
          ×
        </button>
        <div className="text-center font-medium">로그인</div>
        <div className="w-4"></div>
      </div>

      {/* 메인 콘텐츠 */}
      <div className="flex-1 p-6 flex flex-col">
        {/* 로고 및 부제 */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-emerald-500">{SITE_NAME}</h1>
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
            className="w-full p-3 border rounded-md bg-gray-100"
            required
          />
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleInputChange}
            placeholder="비밀번호"
            className="w-full p-3 border rounded-md bg-gray-100"
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

          {/* 비밀번호 재설정 링크 */}
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

          {/* 소셜 로그인 버튼 - 직접 SVG 구현 */}
          <div className="flex justify-center gap-4">
            <button
              type="button"
              onClick={handleGoogleLogin}
              className="w-12 h-12 rounded-full border flex items-center justify-center bg-white"
            >
              {/* 구글 아이콘 직접 SVG 구현 */}
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
              >
                <path
                  fill="#4285F4"
                  d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z"
                />
                <path
                  fill="#34A853"
                  d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z"
                />
                <path
                  fill="#FBBC05"
                  d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z"
                />
                <path
                  fill="#EA4335"
                  d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z"
                />
              </svg>
            </button>
            <button
              type="button"
              onClick={handleFacebookLogin}
              className="w-12 h-12 rounded-full border flex items-center justify-center bg-blue-600"
            >
              {/* 페이스북 아이콘 직접 SVG 구현 */}
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 320 512"
                fill="white"
              >
                <path d="M279.14 288l14.22-92.66h-88.91v-60.13c0-25.35 12.42-50.06 52.24-50.06h40.42V6.26S260.43 0 225.36 0c-73.22 0-121.08 44.38-121.08 124.72v70.62H22.89V288h81.39v224h100.17V288z" />
              </svg>
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
        {message && <p className="text-green-500 mt-4 text-center">{message}</p>}
        {error && <p className="text-red-500 mt-4 text-center">{error}</p>}
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
        <Link to="/intro" className="flex flex-col items-center">
          <div className="text-emerald-500 font-bold">{SITE_NAME}</div>
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