# ベースイメージ
FROM node:14-alpine

# 作業ディレクトリを設定
WORKDIR /app

# package.json と package-lock.json をコピー
COPY package*.json ./

# 依存関係のインストール
RUN npm ci

# ソースコードをコピー
COPY . .

# 開発サーバーの起動（デフォルトのポートは 3000）
CMD ["npm", "run", "dev"]