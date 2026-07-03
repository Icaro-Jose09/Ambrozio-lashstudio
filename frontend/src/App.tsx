import { Button } from "@/components/ui/button"

export function App() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center space-y-8">
      <div className="text-center">
        <h1 className="text-5xl font-bold text-ambrozio-brown mb-4 tracking-tighter">
          Ambrozio Lash Studio
        </h1>
        <p className="text-lg text-ambrozio-dark opacity-80">
          O sistema de gestão da sua beleza.
        </p>
      </div>

      <Button className="bg-ambrozio-brown hover:bg-ambrozio-dark text-white px-8 py-6 text-lg rounded-full shadow-lg transition-all duration-300">
        Agendar Atendimento
      </Button>
    </div>
  )
}

export default App