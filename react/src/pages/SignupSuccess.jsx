import { useNavigate } from "react-router-dom";
import { SITE_NAME } from "../constants";

export default function SignupSuccess() {
  const navigate = useNavigate();

  return (
    <div className="max-w-md mx-auto h-screen flex flex-col bg-white justify-center items-center">
      <div className="text-center">
        <h1 className="text-3xl font-bold text-emerald-500 mb-4">{SITE_NAME}</h1>
        <p className="text-sm text-gray-600 mb-8">유학생을 위한 생활 팁스 서비스</p>
        <p className="text-lg font-medium mb-4">회원가입이 완료되었습니다!</p>
        <p className="text-sm text-gray-600 mb-8">
          회원가입이 완료되었습니다! 이제 Road!t의 모든 기능을 사용해 보세요.
        </p>
        <button
          onClick={() => navigate("/")}
          className="w-full p-3 bg-emerald-500 text-white rounded-md"
        >
          홈으로 가기
        </button>
      </div>
    </div>
  );
}