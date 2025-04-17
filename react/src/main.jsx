import React from "react"
import ReactDOM from "react-dom/client"
import AppRouter from "./routes/Router.jsx" 
import "./index.css"
import { ThemeProvider } from "./components/theme-provider" 

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ThemeProvider attribute="class" defaultTheme="light" enableSystem disableTransitionOnChange>
      <AppRouter />
    </ThemeProvider>
  </React.StrictMode>
)
