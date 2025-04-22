
import { QRCodeSVG } from 'qrcode.react';
import { Card, CardHeader, CardContent, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { QrCode, RefreshCw } from "lucide-react";

const WaitingQRCode = () => {
  const registrationUrl = `${window.location.origin}/register`;

  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center gap-2">
          <QrCode className="h-5 w-5" />
          QR Code para Registro
        </CardTitle>
      </CardHeader>
      <CardContent className="flex flex-col items-center space-y-4">
        <div className="p-4 bg-white rounded-lg">
          <QRCodeSVG value={registrationUrl} size={200} />
        </div>
        <p className="text-sm text-muted-foreground text-center">
          Escaneie o QR Code para acessar o formulário de registro
        </p>
        <Button variant="outline" size="sm" onClick={() => window.location.reload()}>
          <RefreshCw className="h-4 w-4 mr-2" />
          Atualizar QR Code
        </Button>
      </CardContent>
    </Card>
  );
};

export default WaitingQRCode;
