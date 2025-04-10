
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Learning from "./Learning";

const Index = () => {
  // Now we're using Learning as our root page, so this component
  // can either redirect to Learning or render it directly
  return <Learning />;
};

export default Index;
