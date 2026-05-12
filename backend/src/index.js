const express = require('express');
const cors = require('cors');
const dotenv = require('dotenv');

dotenv.config();

const app = express();

// Routes
const authRoutes = require('./routes/auth');
const projectRoutes = require('./routes/projects');

// Middleware
app.use(cors());
app.use(express.json());
app.use('/uploads', express.static('uploads'));

// API Routes
app.use('/api/auth', authRoutes);
app.use('/api/projects', projectRoutes);

// Basic Route
app.get('/', (req, res) => {
    res.json({ message: 'ProManage API is running...' });
});

// Database Connection
const PORT = process.env.PORT || 5000;
const sequelize = require('./config/database');

sequelize.sync({ alter: true })
    .then(() => {
        console.log('Connected to SQLite Database via Sequelize');
        app.listen(PORT, "0.0.0.0", () => {
            console.log(`Server running on port ${PORT}`);
        });
    })
    .catch(err => {
        console.error('Database connection error:', err);
    });
