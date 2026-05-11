const mongoose = require('mongoose');

const ProjectSchema = new mongoose.Schema({
    name: { type: String, required: true },
    description: String,
    domain: String,
    goals: [String],
    leader: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    members: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }],
    mentor: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    status: { type: String, enum: ['pending', 'active', 'reviewed', 'approved', 'completed'], default: 'pending' },
    progress: { type: Number, default: 0 },
    files: [{
        name: String,
        url: String,
        uploadedAt: { type: Date, default: Date.now }
    }],
    reviews: [{
        marks: Number,
        feedback: String,
        reviewer: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
        createdAt: { type: Date, default: Date.now }
    }],
    createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('Project', ProjectSchema);
