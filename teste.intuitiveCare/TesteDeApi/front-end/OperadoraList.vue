<template>
  <div>
    <h1>Busca de Operadoras de Saúde</h1>
    <div class="search-container">
      <input
          type="text"
          v-model="searchTerm"
          @input="buscarOperadoras"
          placeholder="Digite um termo de busca..."
      />
      <button @click="buscarOperadoras">Buscar</button>
    </div>

    <div v-if="loading" class="loading">Carregando...</div>

    <div v-if="operadoras.length > 0" class="results">
      <h2>Resultados ({{ operadoras.length }})</h2>
      <div class="operadora-card" v-for="operadora in operadoras" :key="operadora.registroAns">
        <h3>{{ operadora.nomeFantasia || operadora.razaoSocial }}</h3>
        <p><strong>Registro ANS:</strong> {{ operadora.registroAns }}</p>
        <p><strong>CNPJ:</strong> {{ operadora.cnpj }}</p>
        <p><strong>Localização:</strong> {{ operadora.cidade }}/{{ operadora.uf }}</p>
        <p><strong>Contato:</strong> {{ operadora.telefone }}</p>
      </div>
    </div>

    <div v-if="noResults" class="no-results">
      Nenhuma operadora encontrada para "{{ searchTerm }}"
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      searchTerm: '',
      operadoras: [],
      loading: false,
      noResults: false
    };
  },
  methods: {
    async buscarOperadoras() {
      if (this.searchTerm.trim() === '') {
        this.operadoras = [];
        this.noResults = false;
        return;
      }

      this.loading = true;
      this.noResults = false;

      try {
        const response = await axios.get(`http://localhost:8080/api/operadoras/buscar`, {
          params: { termo: this.searchTerm }
        });

        this.operadoras = response.data;
        this.noResults = this.operadoras.length === 0;
      } catch (error) {
        console.error('Erro na busca:', error);
        this.operadoras = [];
        this.noResults = true;
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.search-container {
  margin: 20px 0;
  display: flex;
  gap: 10px;
}

input {
  padding: 10px;
  width: 300px;
  font-size: 16px;
}

button {
  padding: 10px 20px;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #369f6b;
}

.operadora-card {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 15px;
  margin: 10px 0;
  background-color: #f9f9f9;
}

.loading {
  margin: 20px 0;
  color: #666;
}

.no-results {
  margin: 20px 0;
  color: #666;
  font-style: italic;
}
</style>