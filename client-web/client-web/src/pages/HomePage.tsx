import Document from "../components/Document";

function HomePage() {
    const documents = [
        { name: "Contrato de Prestação de Serviços", description: "Contrato estabelecendo as condições de prestação de serviços entre cliente e fornecedor." },
        { name: "Termo de Confidencialidade", description: "Documento para proteger informações confidenciais trocadas entre as partes." },
        { name: "Proposta Comercial", description: "Proposta com os detalhes dos serviços oferecidos, preços e prazos." },
    ];

    return (
        <div>
            <h1 className="text-xl">Documentos</h1>
            <div className="flex gap-2">
                {documents.map((doc, index) => (
                    <Document key={index} {...doc} />
                )
                )}
            </div>
        </div>
    );
}

export default HomePage;