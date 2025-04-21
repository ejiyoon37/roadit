import { useNavigate } from "react-router-dom";
import { SITE_NAME } from "../constants";

export default function Intro() {
  const navigate = useNavigate();

  return (
    <div className="max-w-md mx-auto h-screen flex flex-col bg-white justify-center items-center">
      <div className="text-center">
        {/* 로고 */}
        <img
          src="/로딧로고.png"
          alt="Road!t Logo"
          className="mx-auto mb-4 h-16"
        />
        <p className="text-sm text-gray-600 mb-8">유학생을 위한 생활 팁스 서비스</p>

        {/* 캐릭터 이미지 */}
        <div className="mb-8">
          <img
            src="/로딧캐릭터.png"
            alt="Road!t Character"
            className="mx-auto w-24 h-24"
          />
        </div>

        {/* 환영 메시지 */}
        <p className="text-lg font-medium mb-2">Chào mừng đến với Road!t</p>
        <p className="text-sm text-gray-600 mb-8">
          베트남 유학생을 위한 최고의 플랫폼입니다!
        </p>

        {/* 로그인 버튼 */}
        <button
          onClick={() => navigate("/")}
          className="w-full p-3 bg-emerald-500 text-white rounded-md"
        >
          로그인 페이지로 이동
        </button>
      </div>
    </div>
  );
}