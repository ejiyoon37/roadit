import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "../pages/Login";
import Signup from "../pages/Signup";
import ResetPassword from "../pages/ResetPassword";
import ChangePassword from "../pages/ChangePassword";
import GoogleLoginCallback from "../pages/GoogleLoginCallback";
import SignupSuccess from "../pages/SignupSuccess";
import Intro from "../pages/Intro"; // Intro 컴포넌트 임포트

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route path="/change-password" element={<ChangePassword />} />
        <Route path="/google-callback" element={<GoogleLoginCallback />} />
        <Route path="/signup-success" element={<SignupSuccess />} />
        <Route path="/intro" element={<Intro />} /> {/* /intro 경로 추가 */}
      </Routes>
    </BrowserRouter>
  );
}